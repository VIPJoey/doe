(function($){
    $.fn.extend({
        jsonEdit: function(options) {

            //参数绑定
            var params = {
                debug:false
                ,className:'jsonEdit'
                ,buttonSelector:''
                ,height:100
                ,html:false //TODO 是否html转义
                ,highlight:false //TODO 是否高亮
                ,dynamic:false
                ,tabIndent:true
                ,errorAlert:false
                ,onSuccess:null       
                ,onError:null       
            };
            $.extend(params,options);

            //Console
            if(typeof console === "undefined"){
                console = {log:function(){
                    //TODO
                    for(var i in arguments){
                        var elem = arguments[i],
                            type = (typeof elem),
                            info = '===Log Info===\n';
                        switch(type){
                            case "string":
                                info += elem;
                                break;
                            case "object":
                                info += (JSON.stringify(elem));
                                break;
                            case "number":
                                info += elem;
                                break;
                            default:
                        }
                        alert(info);
                    }
                }};
            }

            var pasteHtmlAtCaret = function(html){
                var sel, range;
                if (window.getSelection) {
                    // IE9 and non-IE
                    sel = window.getSelection();
                    if (sel.getRangeAt && sel.rangeCount) {
                        range = sel.getRangeAt(0);
                        range.deleteContents();

                        // Range.createContextualFragment() would be useful here but is
                        // non-standard and not supported in all browsers (IE9, for one)
                        var el = document.createElement("div");
                        el.innerHTML = html;
                        var frag = document.createDocumentFragment(), node, lastNode;
                        while ((node = el.firstChild)) {
                            lastNode = frag.appendChild(node);
                        }
                        range.insertNode(frag);

                        // Preserve the selection
                        if (lastNode) {
                            range = range.cloneRange();
                            range.setStartAfter(lastNode);
                            range.collapse(true);
                            sel.removeAllRanges();
                            sel.addRange(range);
                        }
                    }
                } else if (document.selection && document.selection.type != "Control") {
                    // IE < 9
                    sel = document.selection;
                    sel.createRange().pasteHTML(html);
                }
            };

            //Construct
            var JsonEdit = function( $target,$edit,params ){
                this.$target = $target;
                this.$edit = $edit;
                this.params = params;
            };

            JsonEdit.prototype = {
                init:function(){
                    var $edit = this.$edit,
                        $target = this.$target,
                        params = this.params,
                        that = this;

                    //input bindding
                    $edit.bind('input propertychange paste',function(e){
                        //延迟以接收paste
                        setTimeout(function(){
                            //调试输出
                            if( params.debug === true ){
                                //console.log('HTML \n'+$edit.html());
                                console.log('TEXT \n'+$edit.text());
                            }

                            //动态格式化
                            if( params.dynamic === true ){
                                that.doFormat();
                            }
                        },10);

                    });

                    //button bindding
                    if( params.buttonSelector ){
                        var $btn = $(params.buttonSelector);
                        if( $btn.length >0 ){
                            $btn.bind('click',function(e){
                                e.preventDefault();
                                that.doFormat();
                            });
                        }
                    }

                    //keydown bindding
                    $edit.bind('keydown',function(e){
                        //var obj = document.activeElement;

                        //tabIndent bindding
                        if( params.tabIndent && e.keyCode == 9){
                            e.preventDefault();
                            pasteHtmlAtCaret('\t');
                            $edit.trigger('input');
                        }

                    });

                    //onpaste filter
                    $edit.bind('paste',function(e){
                        e.preventDefault();
                        var pastedText;
                    if (window.clipboardData && window.clipboardData.getData) { // IE
                        pastedText = window.clipboardData.getData('Text');
                    } else if (e.originalEvent.clipboardData && e.originalEvent.clipboardData.getData) {
                        pastedText = e.originalEvent.clipboardData.getData('text/plain');
                    }
                        pasteHtmlAtCaret(pastedText);

                    });

                    //value Init
                    var oVal = $target.val();
                    if( oVal ){
                        $edit.text(oVal);
                        this.doFormat();
                    }

                    if( params.debug === true ){
                        console.log('JsonEdit Ready!');
                    }


                }

                //获取当前输入文本
                ,getValue:function(){
                    var text = this.$edit.text();
                    return text;
                }
                //设置文本
                ,setValue:function(val){

                    this.$edit.text(val);
                }

                //执行格式化
                ,doFormat:function(){
                    var result = this.jsonFormat();
                    if( result ){
                        if( this.params.debug === true ){
                            console.log( 'FORMAT \n'+result );
                        }
                        this.setValue( result );

                        if( this.params.onSuccess ){
                            this.params.onSuccess();
                        }
                    }
                }

                //格式化
                ,jsonFormat:function(){
                    var text = this.getValue(),
                        format = "";
                    if( text !== "" ){
                        var parse = "";
                        try{
                            parse = JSON.parse(text);
                            format = JSON.stringify(parse,null,2);

                        }catch(e){
                            this.errorHandle(1,e);
                        }

                        return format;
                    }
                }

                ,errorHandle:function(type,event){
                    var info = "";
                    switch(type){
                        case 1:
                            info = 'Json Syntax Error!';
                            break;
                        default:
                            info = 'Error!';
                    }

                    if( this.params.onError ){
                        this.params.onError();
                    }

                    else if( this.params.errorAlert === true ){
                        alert(info);
                    }
                    if( this.params.debug === true ){
                        console.log(info ,event);
                    }
                }

            //TODO htmlEncode        
            //TODO saveValue        
            //TODO prettify        

            };


            var endResult = [];
            this.each(function() {
                var $target = $(this);

                //创建编辑器区域
                var $edit = $("<pre contenteditable></pre>");
                //$edit.addClass('prettyprint');
                $edit.addClass(params.className);
                $edit.css('height',parseInt(params.height)+'px');
                $edit.css('padding','10px');
                $target.before($edit).hide();

                /*
                $hint = $("<p></p>");
                $hint.css({
                    'display':'block',
                    'width':
                });
                */

                var Edit = new JsonEdit($target,$edit,params);

                Edit.init();

                endResult.push(Edit);

            });

            return (endResult.length==1?endResult[0]:endResult);
        }
    });
})(jQuery);
