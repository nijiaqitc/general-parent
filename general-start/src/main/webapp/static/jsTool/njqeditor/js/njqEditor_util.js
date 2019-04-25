(function () {
	var sysConfig = njqEditor.sysConfig;
    var ignoreNode = sysConfig.ignoreNode;
    var labels = sysConfig.mergeLabel;
    var parentIncludeNode = sysConfig.parentIncludeNode;
    var childIncludeNode = sysConfig.childIncludeNode;
    var spaceNode = sysConfig.spaceNode;
    var singleNode = sysConfig.singleNode;
    var session;

    /**
     * 通用工具区，类似于util工具类
     */
    var util = window.njqEditor.util = {
        // 初始化选区
        initRange: function (node) {
            var firstNode = this.getMinNode(node);
            var range = document.createRange();
            range.setStartBefore(firstNode);
            range.collapse(true);
            this.getSession().removeAllRanges();
            this.getSession().addRange(range);
            return range.cloneRange();
        },
        getSession: function () {
            if (!session) {
                session = window.getSelection();
            }
            return session;
        },
        getRange: function () {
            try {
                this.getSession().getRangeAt(0);
            } catch (e) {
                return;
            }
            return this.getSession().getRangeAt(0).cloneRange();
        },
        //创建选区
        createRange: function () {
            if (document.selection) {
                return document.selection.createRange();
            } else {
                return document.createRange();
            }
        },
        // 设置选区
        setRange: function (range) {
            this.getSession().removeAllRanges();
            this.getSession().addRange(range);
        },
        // 去除首尾空格
        trim: function (str) {
            return str.replace(/(^[ \t\n\r]+)|([ \t\n\r]+$)/g, '');
        },
        /**
         * 将fnode插入到snode之前
         */
        insertBefore: function (fnode, snode) {
            snode.parentNode.insertBefore(fnode, snode);
        },
        /**
         * 将fnode插入到snode之后
         */
        insertAfter: function (fnode, snode) {
            // 插入后置书签节点
            if (snode.nextSibling) {
                snode.parentNode.insertBefore(fnode, snode.nextSibling);
            } else {
                snode.parentNode.appendChild(fnode);
            }
        },
        /**
         * 检测对象是否为空
         */
        isEmptyObject: function (obj) {
            for (var key in obj) {
                return false;
            }
            return true;
        },
        /**
         * 查找按钮的工具按钮 node:开始查找的节点
         */
        findToolBtn: function (node) {
            if (node.toolBtn) {
                return node
            }
            // 循环到最外层有id的为止
            while (!node.id) {
                node = node.parentNode;
                if (node.toolBtn) {
                    return node
                }
            }
            return;
        },
        /**
         * 寻找父弹框
         */
        findParentDialog: function (node) {
            var nodeId = node.btnId;
            // 循环到最外层为止（不包含最外层的div）
            while (!nodeId) {
                node = node.parentNode;
                nodeId = node.btnId;
            }
            return node;
        },
        /**
         * 遍历节点 startNode:遍历开始点 endNode:遍历结束点 fun:对一些节点所需要处理的事件 type:针对的节点类型(-1 文本节点和元素节点)
         * 执行方式是先父节点，然后子节点，然后子兄弟节点，父兄弟节点
         */
        forListNode: function (startNode, endNode, fun, type) {
            var tempNode = startNode;
            if (this.isElementNode(endNode)) {
                // 如果给的结束点是元素节点，那么重新设定结束节点
                endNode = util.getMinLastNode(endNode);
            }
            var flag = false;
            while (!(tempNode.id && tempNode.id == "njqEditor_context")) {
                if (tempNode.checkNext) {
                    if (!tempNode.unclear) {
                        tempNode.checkNext = null;
                    }
                    if (tempNode.contains(endNode)) {
                        return;
                    }
                    tempNode = this.getNextNode(tempNode, endNode);
                }
                if (tempNode == endNode) {
                    // 此处判断主要是针对外层第一次调用时startNode=endNode的情况
                    if (tempNode.needCheck) {
                        flag = true;
                    } else {
                        if (this.isElementNode(tempNode) && tempNode.firstChild) {
                            tempNode = tempNode.firstChild;
                        }
                    }
                }
                if (tempNode.needCheck) {
                    tempNode.needCheck = null;
                }

                if (tempNode.nodeType == 1 && type == 1) {
                    //针对标签节点
                    tempNode = fun(tempNode);
                } else if (tempNode.nodeType == 3 && type == 3) {
                    //针对文本节点
                    tempNode = fun(tempNode);
                } else if (type == -1 && (tempNode.nodeType == 3 || tempNode.nodeType == 1)) {
                    //针对文本节点和标签节点
                    tempNode = fun(tempNode);
                }
                if (!tempNode) {
                    return;
                }
                // 针对注释节点直接删除
                if (tempNode.nodeType == 8) {
                    if (tempNode.previousSibling) {
                        tempNode = tempNode.previousSibling;
                        this.removeNode(tempNode.nextSibling);
                    } else {
                        tempNode = tempNode.parentNode;
                        this.removeNode(tempNode.firstChild);
                    }
                }
                if (flag) {
                    break;
                }


                /**
                 * 判断返回的节点是否需要重新校验(新节点则直接检测)
                 */
                if (!tempNode.newNode) {
                    if (tempNode != endNode) {
                        if (tempNode.checkNext) {
                            if (!tempNode.unclear) {
                                tempNode.checkNext = null;
                            }
                            if (tempNode.contains(endNode)) {
                                //如果出现节点移动，将出现很多问题
                                break;
                            }
                            tempNode = this.getNextNode(tempNode, endNode);
                        } else {
                            tempNode = this.getNextChildNode(tempNode, endNode);
                        }
                    }
                } else {
                    tempNode.newNode = null;
                }
                if (!tempNode) {
                    return;
                }
                tempNode.needCheck = true;
            }
        },
        /**
         * 获取子节点 node:开始节点 parentNode:结束节点
         */
        getNextChildNode: function (node, parentNode) {
            if (node.nodeType == 1 && node.firstChild) {
                return node.firstChild;
            } else {
                if (node.nextSibling) {
                    return this.emptyCheckAndReturn(node, parentNode);
                } else {
                    return this.getNextNode(node, parentNode);
                }
            }
        },
        /**
         * 获取下一个节点，如果当前节点已经是最后的节点 那么获取当前节点的父节点的下一个节点，直到成为最终的父节点为止 node:当前节点
         * parentNode:终止节点
         */
        getNextNode: function (node, parentNode) {
            if (node == parentNode) {
                return node;
            } else if (!node.nextSibling) {
                node = node.parentNode;
                if (node == parentNode) {
                    return node;
                } else {
                    return this.getNextNode(node, parentNode);
                }
            } else {
                return this.emptyCheckAndReturn(node, parentNode);
            }
        },
        emptyCheckAndReturn: function (node, parentNode) {
            // 下个节点是空节点直接删除，并取下一个节点
            if (this.checkIsEmpty(node.nextSibling)) {
                if (node.nextSibling == parentNode) {
                    return parentNode;
                }
                var flag = false;
                /**
                 * 如果要删除的节点包含了结束节点（结束节点是""的情况），则删除节点后不再读取下一个节点
                 */
                if (node.nodeType == 1 && node.nextSibling.contains(parentNode)) {
                    flag = true;
                }
                this.removeNode(node.nextSibling);
                if (flag) {
                    return null;
                }
                return this.getNextNode(node, parentNode);
            }
            return node.nextSibling;
        },
        insertNextNode: function (range, node) {
            if (this.isOutNodeCheck(node)) {
                range.insertNode(node);
                range.setStartBefore(this.getMinNode(node));
            } else {
                var insertNode = this.createCustomNode(constants.DIV);
                insertNode.appendChild(node);
                range.insertNode(insertNode);
                range.setStartBefore(this.getMinNode(insertNode));
            }
            range.collapse(true);
            return range;
        },
        insertCustomNode: function (sNode, nNode, type) {
            if (this.isOutNodeCheck(sNode)) {
                if (type == 1) {
                    this.insertBefore(sNode, nNode);
                } else {
                    this.insertAfter(sNode, nNode);
                }
                return sNode;
            } else {
                var insertNode = this.createCustomNode(constants.DIV);
                insertNode.appendChild(sNode);
                if (type == 1) {
                    this.insertBefore(insertNode, nNode);
                } else {
                    this.insertAfter(insertNode, nNode);
                }
                return insertNode;
            }
        },
        /**
         * 获取从当前节点到editorContext节点之前所有的父节点名称 currentNode:当前节点
         */
        getAllParentNodesName: function (currentNode) {
            var parentsName = [];
            // 循环到最外层有id的节点为止（不包含最外层的div）
            while (!currentNode.id) {
                parentsName.push(currentNode.tagName)
                currentNode = currentNode.parentNode;
            }
            return parentsName;
        },
        /**
         * 获取从当前节点到editorContext节点之前所有的父节点 currentNode:当前节点
         */
        getAllParentNodes: function (currentNode) {
            var parents = [];
            while (!currentNode.id) {
                parents.push(currentNode)
                currentNode = currentNode.parentNode;
            }
            return parents;
        },
        /**
         * 获取从当前节点到指定父节点间的所有节点（包含父节点） pNode:指定父节点 cNode:当前节点
         */
        getCustomParentNodes: function (pNode, cNode) {
            var parents = [];
            while (cNode != pNode) {
                parents.push(cNode)
                cNode = cNode.parentNode;
            }
            parents.push(pNode)
            return parents;
        },
        /**
         * 选区获取最外层节点 node:针对的节点 p:1 开始位置 2 结束位置
         */
        getRangeOutParentNode: function (node, range, p) {
            if (node.id) {
                if (p == 1) {
                    return node.childNodes[range.startOffset];
                }
                if (p == 2) {
                    return node.childNodes[range.endOffset - 1];
                }
            }
            var innerOutParent = this.getOutParentNode(node);
            // 此处处理主要是针对 <li><table/></li>的场景，最外层的应该是ol，而不是table
            if (innerOutParent.parentNode.id) {
                return innerOutParent;
            } else {
                return this.getOutParentNode(innerOutParent.parentNode);
            }
        },
        /**
         * 正常节点获取外层节点
         *
         * @param name
         */
        getOutParentNode: function (node) {
            while (!node.id) {
                if (this.isOutNodeCheck(node)) {
                    return node;
                } else {
                    node = node.parentNode;
                }
            }
        },
        /**
         * 获取某个特定的父节点 name:特定标签的父节点 cNode:当前节点
         */
        getSpecalParentNode: function (name, cNode, styleName, styleValue) {
            var name = name.toUpperCase();
            while (cNode) {
                if (this.isTextNode(cNode)) {
                    cNode = cNode.parentNode;
                } else {
                    if (cNode.id) {
                        return;
                    }
                    if (cNode.tagName != name) {
                        cNode = cNode.parentNode;
                    } else {
                        if (styleName) {
                            if (cNode.style[styleName] == styleValue) {
                                return cNode;
                            } else {
                                cNode = cNode.parentNode;
                            }
                        } else {
                            return cNode;
                        }
                    }
                }
            }
            return cNode;
        },
        /**
         * 获取上个节点标签名和样式相同的父节点
         */
        getContainsStyleParentNode: function (parentNode, addNode) {
            var nodeStyle = addNode.getAttribute("style");
            var styles = this.trim(addNode.style.cssText).split(";");
            var styleObj = {}, temp;
            for (var i = 0; i < styles.length; i++) {
                temp = styles[i].split(":");
                if (temp.length > 1) {
                    styleObj[this.trim(temp[0])] = this.trim(temp[1]);
                }
            }
            while (parentNode) {
                if (parentNode.id) {
                    return;
                }
                if (parentNode.tagName == addNode.tagName) {
                    if (nodeStyle) {
                        if (this.checkNodeContainsStyleAndClass(parentNode,
                            addNode.classList, styleObj)) {
                            return parentNode;
                        } else {
                            parentNode = parentNode.parentNode;
                        }
                    } else {
                        return parentNode;
                    }
                } else {
                    parentNode = parentNode.parentNode;
                }
            }
        },
        /**
         * 获取连续前一个包含当前节点样式的父节点 连续的标签即：<span><span>[aaa]</span></span> type
         * 有值获取后一个 没值获取前一个
         */
        getSeriesContainsStyleParentNode: function (parentNode, addNode, type) {
            var nodeStyle = addNode.getAttribute("style");
            var checkNode;
            while (parentNode) {
                if (parentNode.id) {
                    return;
                }
                if (nodeStyle) {
                    if (type) {
                        checkNode = parentNode.nextSibling;
                    } else {
                        checkNode = parentNode.previousSibling;
                    }
                    if (!checkNode) {
                        parentNode = parentNode.parentNode;
                        if (parentNode.tagName == addNode.tagName) {
                            return parentNode;
                        }
                    } else {
                        return;
                    }
                } else {
                    parentNode = parentNode.parentNode;
                    if (parentNode.tagName == addNode.tagName) {
                        return parentNode;
                    }
                }
            }
        },
        /**
         * 获取指定样式名称，样式内容不为空的节点
         */
        getParentPointStyleNameNode: function (parentNode, styleName) {
            if (parentNode.nodeType == 3) {
                parentNode = parentNode.parentNode;
            }
            while (!parentNode.id) {
                if (parentNode.style[styleName]) {
                    return parentNode;
                } else {
                    parentNode = parentNode.parentNode;
                }
            }
        },
        /**
         * 获取包含样式但不包含标签的父节点
         */
        getContainsStyleParentNodeUntagName: function (parentNode, styleObj) {
            if (parentNode.nodeType == 3) {
                parentNode = parentNode.parentNode;
            }
            while (!parentNode.id) {
                if (this.checkNodeContainsStyleAndClass(parentNode, [],
                    styleObj)) {
                    return parentNode;
                } else {
                    parentNode = parentNode.parentNode;
                }
            }
        },
        /**
         * 获取所有指定［标签名称］的子节点
         */
        getElementsByTagName: function (node, tagName) {
            if (node.getElementsByTagName) {
                //将HTMLCollection集合转换成数组类型（保持上下一致，HTMLCollection集合是动态的 移除后长度会减少，数组是不变的，不保持一致的话for循环不好写）
                return Array.prototype.slice.call(node.getElementsByTagName(tagName), 0);
            } else {
                var tagNodeList = [];
                this.forListNode(node.firstChild, node, function (n) {
                    if (n.tagName == tagName) {
                        tagNodeList.push(n);
                    }
                    return n;
                }, 1);
                return tagNodeList;
            }
        },
        /**
         * 获取与btnNode［标签、样式、样式类］相同的子节点
         */
        getElementsByNode: function (node, btnNode) {
            var tagNodeList = [];
            this.forListNode(node.firstChild, node, function (n) {
                if (n == node) {
                    return n;
                }
                if (n.tagName == btnNode.tagName) {
                    if (util.sameNodeCompare(n, btnNode)) {
                        tagNodeList.push(n);
                    }
                }
                return n;
            }, 1);
            return tagNodeList;
        },
        /**
         * 查询所有子标签 删除前一个节点里面的子节点与后一个节点样式名相同的样式 如果样式为空了，那么将自节点中的子节点移动到外面
         *
         */
        delSameLabelOrCss: function (dealNode, compareNode) {
            var tagNodeList = dealNode
                .getElementsByTagName("*");
            for (var i = 0; i < tagNodeList.length; i++) {
                if (this.checkParentContainsSameStyName(tagNodeList[i],
                    compareNode)) {
                    this.delSameStyleName(tagNodeList[i], compareNode);
                }
                //相同的标签且无样式直接删除
                if (tagNodeList[i].tagName == compareNode.tagName && tagNodeList[i].style.cssText.trim() == constants.EMPTY) {
                    this.moveAllChildNodeToOut(tagNodeList[i]);
                    // 因为移除后数组的长度减1了
                    --i;
                }
            }
        },
        /**
         * 获取与后一个节点标签明相同，后一个包含前一个样式的所有子节点
         */
        getContainsStyleChilds: function (node, btnNode) {
            var nodeList = node.getElementsByTagName(btnNode.tagName);
            var list = [];
            for (var n in nodeList) {
                if (nodeList[n].style.cssText != null) {
                    if (!this.checkParentContainsChild(nodeList[n], btnNode)) {
                        continue;
                    }
                }
                list.push(nodeList[n]);
            }
            return list;

        },
        /**
         * 获取下个文本节点，循环到endNode时返回endNode
         */
        getNextTextNode: function (node, endNode) {
            if (node == endNode) {
                return node;
            } else if (!node.nextSibling) {
                node = node.parentNode;
                if (node == endNode) {
                    return node;
                } else {
                    return this.getNextTextNode(node, endNode);
                }
            } else {
                // 下个节点是空节点直接删除，并取下一个节点
                if (this.checkIsEmpty(node.nextSibling)) {
                    this.removeNode(node.nextSibling);
                    return this.getNextTextNode(node, endNode);
                } else {
                    node = node.nextSibling;
                    if (this.isElementNode(node)) {
                        var minNode = this.getMinNode(node);
                        if (minNode.nodeType == 3) {
                            return minNode;
                        } else {
                            return this.getNextTextNode(minNode, endNode);
                        }
                    } else {
                        return node;
                    }
                }
            }
        },
        /**
         * 获取循环到最底层的开始节点
         */
        getMinNode: function (node) {
            if (!node) {
                return;
            }
            //排除掉标签节点
            if (node.unclear) {
                if (node.nextSibling) {
                    return this.getMinLastNode(node.nextSibling);
                } else {
                    return node.parentNode;
                }
            }
            if (node.nodeType == 1 && node.firstChild) {
                return this.getMinNode(node.firstChild);
            } else {
                return node;
            }
        },
        /**
         * 获取循环到最底层的结束节点
         *
         * @param node
         * @returns
         */
        getMinLastNode: function (node) {
            if (!node) {
                return;
            }
            //排除掉标签节点
            if (node.unclear) {
                if (node.previousSibling) {
                    return this.getMinLastNode(node.previousSibling);
                } else {
                    return node.parentNode;
                }
            }
            if (node.nodeType == 1 && node.lastChild) {
                return this.getMinLastNode(node.lastChild);
            } else {
                return node;
            }
        },
        /**
         * 获取前一个节点，如果当前节点已经是最前的节点 那么获取当前节点的父节点的前一个节点，知道成为最终的父节点为止 node:当前节点
         * parentNode:父节点
         */
        getBeforeNode: function (node, parentNode) {
            if (node == parentNode) {
                return node;
            } else if (!node.previousSibling) {
                node = node.parentNode;
                return this.getBeforeNode(node, parentNode);
            } else {
                return node = node.previousSibling;
            }
        },
        /**
         * 获取前一个文本节点 node: 从当前节点往前寻找文本节点
         */
        getBeforeTextNode: function (node, sNode) {
            if (node == sNode) {
                return node;
            } else if (!node.previousSibling) {
                node = node.parentNode;
                if (node == sNode) {
                    return node;
                } else {
                    return this.getBeforeTextNode(node, sNode);
                }
            } else {
                // 下个节点是空节点直接删除，并取下一个节点
                if (this.checkIsEmpty(node.previousSibling)) {
                    this.removeNode(node.previousSibling);
                    return this.getBeforeTextNode(node, sNode);
                } else {
                    node = node.previousSibling;
                    if (this.isElementNode(node)) {
                        var minNode = this.getMinLastNode(node);
                        if (minNode.nodeType == 3) {
                            return minNode;
                        } else {
                            return this.getBeforeTextNode(minNode, sNode);
                        }
                    } else {
                        return node;
                    }
                }
            }
        },
        /**
         * 获取节点中第一个text节点
         */
        getfirstTextNode: function (node) {
            if (!node) {
                return;
            }
            // 如果给的标签是文本节点标签，那么直接返回
            if (this.isTextNode(node)) {
                // 如果是填充的字符，那么返回null
                if (this.isFillChar(node)) {
                    return;
                }
                return node;
            }
            // 判断是否含有子节点
            if (!node.firstChild) {
                return;
            }
            var nodeChildren = node.childNodes;
            var textNode = null, backNode = null;
            // 递归调用获取各个子节点的文本节点
            for (var i = 0; i < nodeChildren.length; i++) {
                backNode = this.getfirstTextNode(nodeChildren[i]);
                if (backNode) {
                    textNode = backNode;
                    break;
                }
            }
            return textNode;
        },
        /**
         * 获取节点中最后一个text节点
         */
        getlastTextNode: function (node) {
            if (!node) {
                return;
            }
            // 如果给的标签是文本节点标签，那么直接返回
            if (this.isTextNode(node)) {
                if (this.isEmptyTextNode(node)) {
                    return;
                }
                // 如果是填充的字符，那么返回null
                if (this.isFillChar(node)) {
                    return;
                }
                return node;
            }
            // 节点是否存在子节点
            if (!node.lastChild) {
                return;
            }
            var nodeChildren = node.childNodes;
            var textNode = null, backNode = null;
            // 递归调用获取各个子节点的文本节点
            for (var i = nodeChildren.length - 1; i >= 0; i--) {
                backNode = this.getlastTextNode(nodeChildren[i]);
                if (backNode) {
                    textNode = backNode;
                    break;
                }
            }
            return textNode;
        },
        /**
         * 获取当前节点在父节点中的位置 pNode:父节点 cNode:当前节点
         */
        getNodeIndex: function (pNode, cNode) {
            var nodeList = cNode.parentNode.childNodes;
            for (var i = 0; i < nodeList.length; i++) {
                if (nodeList[i] == cNode) {
                    return i
                }
            }
        },
        /**
         * 获取选区结束节点
         */
        getEndRangeNode: function (range) {
            if (range.endOffset > 0) {
                return range.endContainer.childNodes[range.endOffset - 1];
            } else {
                return range.endContainer.childNodes[range.endOffset];
            }
        },
        /**
         * 获取内部文本节点，如果内部没有则获取前一个文本节点
         */
        getInnerOrBeforeTextNode: function (node) {
            var text = this.getlastTextNode(node);
            if (text) {
                return text;
            } else {
                return this.getBeforeTextNode(node);
            }
        },
        /**
         * 绑定同一个事件 obj：处理的对象 eventType：事件类型 click specalHandle：执行方法
         * commonType：绑定通用类 otobj：执行时的对象，比如 按了这个按钮，另一个控件出现反应
         * otevent：自定义事件，并非在event里面设置好的
         */
        addCommonEventListener: function (commonEvent,obj, eventType, specalHandle,
                                          commonType, otobj, otevent) {
            if (!obj.njqEvent) {
                obj.njqEvent = {};
            }
            // ---------------此两个事件目前主要用于弹窗关闭进行重置选区↓-----------------
            if (otobj != null) {
                obj.njqEvent["otobj"] = otobj;
            }
            if (otevent != null) {
                obj.njqEvent["otevent"] = otevent;
            }
            // ---------------此两个事件目前主要用于弹窗关闭进行重置选区↑-----------------
            var bandFlag = true;
            if (!obj.njqEvent[eventType]) {
                obj.njqEvent[eventType] = [];
                bandFlag = false;
            }
            // 绑定自定义事件，允许绑定多个事件
            obj.njqEvent[eventType].push(specalHandle);
            if (bandFlag) {
                // 已经绑定过一次后就不再执行后续操作
                return;
            }
            if (commonType == 1) {
                // 所有按钮绑定事件
                obj.njqEvent["common"] = "_commonController";
            } else if (commonType == 2) {
                // 所有弹框绑定事件
                obj.njqEvent["common"] = "_commonDialogEventController";
            } else if (commonType == 3) {
                // 编辑器绑定事件
                obj.njqEvent["common"] = "_commonSysEventController";
            } else if (commonType == 4) {
                // document绑定事件
                obj.njqEvent["common"] = "_commonDocEventController";
            } else if (commonType == 5) {
                // 其他的绑定事件
                obj.njqEvent["common"] = "_commonOtherEventController";
            } else if (commonType == 6) {
                // 执行自定义方法
                obj.njqEvent["common"] = "_commonCustomEventController";
            }
            this.addEventListener(obj, eventType,commonEvent["_totalBandEvent"]);
        },
        /**
         * 事件绑定，不同浏览器绑定事件不一样 obj:事件对象 type:绑定事件名称 handle:绑定事件方法
         */
        addEventListener: function (obj, type, handle) {
            try {
                obj.addEventListener(type, handle, false);
            } catch (e) {
                try {
                    obj.attachEvent("on" + type, handle);
                } catch (e) {
                    obj["on" + type] = handle;
                }
            }
        },
        /**
         * 移除监听 obj:事件对象 type:绑定事件名称 handle:绑定事件方法
         */
        removeListener: function (obj, type, handle) {
            obj.removeEventListener(type, handle);
        },
        /**
         * 判断是否是文本节点，是：true node:当前节点
         */
        checkIsTextNode: function (node) {
            if (!node) {
                return false;
            } else if (this.isTextNode(node)) {
                return true;
            } else {
                return false;
            }
        },
        /**
         * 判断当前标签是否是可按下的标签 node:判断的标签
         */
        checkIslabelBtn: function (node) {
            if (this.indexOf(labels, node.tagName) > -1) {
                return true;
            } else {
                return false;
            }
        },
        /**
         * 向上合并相同节点，只进行合并一次 range:选区 cNode:当前节点
         */
        mergePreNode: function (cNode) {
            /*
			 * 如果当前节点是文本节点， 那么获取其父节点即属性节点
			 */
            if (this.isTextNode(cNode)) {
                cNode = cNode.parentNode;
            }
            // 判断当前标签是否是允许合并的标签
            if (this.checkIslabelBtn(cNode)) {
                var pNode = cNode.previousSibling;
                var newNode;
                while (pNode) {
                    if (this.delEmptyNode(pNode)) {
                        pNode = cNode.previousSibling;
                        continue;
                    }
                    if (pNode.id) {
                        break;
                    }
                    if (this.checkIsMergeLabel(cNode, pNode)) {
                        newNode = this.mergeToPreNode(pNode, cNode);
                    }
                    break;
                }
                if (newNode) {
                    this.mergePreNode(newNode);
                    return pNode;
                }
            }
            return cNode;
        },
        /**
         * 向下合并相同节点,只允许合并一次 cNode:当前节点
         */
        mergeNextNode: function (cNode) {
            /*
			 * 如果当前节点是文本节点， 那么获取其父节点即属性节点
			 */
            if (this.isTextNode(cNode)) {
                cNode = cNode.parentNode;
            }
            // 判断当前标签是否是允许合并的标签
            if (this.checkIslabelBtn(cNode)) {
                var nNode = cNode.nextSibling;
                var newNode;
                while (nNode) {
                    if (this.delEmptyNode(nNode)) {
                        nNode = cNode.nextSibling;
                        continue;
                    }
                    if (nNode.id) {
                        break;
                    }
                    /*
					 * 判断两个节点是否可以融合
					 */
                    if (this.checkIsMergeLabel(cNode, nNode)) {
                        newNode = this.mergeToPreNode(cNode, nNode);
                    }
                    break;
                }
                if (newNode) {
                    this.mergeNextNode(newNode);
                }
            }
            return cNode;
        },
        /**
         * 节点合并，当前节点合并到前一个节点中 pNode:前一个节点 cNode:当前节点
         */
        mergeToPreNode: function (pNode, cNode) {
            var childNodeList = cNode.childNodes;
            var firstNode = cNode.firstChild;
            for (var i = 0; childNodeList.length > 0;) {
                pNode.appendChild(cNode.firstChild);
            }
            this.removeNode(cNode);
            return firstNode;
        },
        /**
         * 节点合并(向下合并)，当前节点合并到后一个节点中 cNode:当前节点 nNode:后一个节点
         */
        mergeToNextNode: function (cNode, nNode) {
            var childNodeList = cNode.childNodes;
            var lastNode = cNode.lastChild;
            for (var i = 0; childNodeList.length > 0;) {
                nNode.insertBefore(cNode.lastChild, nNode.firstChild);
            }
            this.removeNode(cNode);
            return lastNode;
        },
        /**
         * 当前节点移动到其他节点 cNode:当前节点 oNode:其他节点
         */
        mergeToOtherNode: function (cNode, oNode) {
            var childNodeList = cNode.childNodes;
            var firstNode = cNode.firstChild;
            for (var i = 0; childNodeList.length > 0;) {
                oNode.appendChild(cNode.firstChild);
            }
            return firstNode;
        },
        /**
         * 将前一个节点及以后的节点移动到后一个节点的后面
         */
        moveCnodeAfter: function (cNode, oNode) {
            var tempNode = cNode;
            while (tempNode) {
                cNode = tempNode;
                tempNode = cNode.nextSibling;
                this.insertAfter(cNode, oNode);
                oNode = cNode;
            }
        },
        /**
         * 将前一个节点移动到后一个节点的前面
         */
        moveToNodeBefore: function (cNode, oNode) {
            var childNodeList = cNode.childNodes;
            for (var i = 0; childNodeList.length > 0;) {
                this.insertBefore(cNode.firstChild, oNode);
            }
        },
        /**
         * 将前一个节点移动到后一个节点的后面
         */
        moveToNodeAfter: function (cNode, oNode) {
            var childNodeList = cNode.childNodes;
            for (var i = 0; childNodeList.length > 0;) {
                this.insertAfter(cNode.firstChild, oNode);
                oNode = oNode.nextSibling;
            }
        },
        /**
         * 将前一个节点中的所有节点移动到后一个节点中
         *
         * @param fNode
         * @param sNode
         */
        changeChildNode: function (fNode, sNode) {
            var childs = fNode.childNodes;
            for (var i = 0; childs.length > 0;) {
                sNode.appendChild(childs[i]);
            }
        },
        /**
         * 融合样式 sNode:被融合的节点 btnNode:提取样式的节点
         */
        mergeStyle: function (sNode, btnNode) {
            // .replace(/\s/g, "")
            var sStyle = this.trim(sNode.style.cssText).split(";");
            var eStyle = this.trim(btnNode.style.cssText).split(";");
            var nodeStyle = {};
            var temp;
            // 提取前一个节点的样式
            for (var i in sStyle) {
                temp = sStyle[i].split(":");
                if (temp.length > 1) {
                    nodeStyle[this.trim(temp[0])] = this.trim(temp[1]);
                }
            }
            // 提取后一个节点的样式
            for (var i in eStyle) {
                temp = eStyle[i].split(":");
                if (temp.length > 1) {
                    nodeStyle[this.trim(temp[0])] = this.trim(temp[1]);
                }
            }
            var style = constants.EMPTY;
            // 将提取出来的节点样式进行赋值
            for (var i in nodeStyle) {
                if (i != constants.EMPTY) {
                    style += i + ":" + nodeStyle[i] + ";"
                }
            }
            sNode.style.cssText = style;
        },
        /**
         * 批量移动节点中的内容到外面
         */
        removeListNode: function (nodeList) {
            for (var j = 0; j < nodeList.length; j++) {
                // 将节点中的内容移到外面
                this.moveAllChildNodeToOut(nodeList[j]);
            }
        },
        /**
         * 将子节点移动到节点外面 pNode:移动的节点
         * dealFlag:是否删除传值则不删
         */
        moveAllChildNodeToOut: function (pNode, dealFlag) {
            //如果是空节点，直接处理了，不用做移动
            if (this.checkIsEmpty(pNode)) {
                if (!dealFlag) {
                    this.removeNode(pNode);
                }
                return;
            }
            var childList = pNode.childNodes;
            var nextNode = pNode;
            /*
			 * 对节点中的内容移动到外面
			 */
            for (var i = 0; childList.length > 0;) {
                if (dealFlag) {
                    this.insertAfter(childList[i], nextNode);
                    nextNode = nextNode.nextSibling;
                } else {
                    if (!this.delEmptyNode(childList[i])) {
                        this.insertAfter(childList[i], nextNode);
                        nextNode = nextNode.nextSibling;
                    }
                }
            }
            if (!dealFlag) {
                this.removeNode(pNode);
            }
        },
        /**
         * 将removeNode中的所有子节点移动到otherNode中
         *
         * @param removeNode
         * @param otherNode
         */
        removeChildrenToOtherNode: function (removeNode, otherNode) {
            if (removeNode.firstChild) {
                otherNode.appendChild(removeNode.firstChild);
            }
            if (removeNode.firstChild) {
                this.removeChildrenToOtherNode(removeNode, otherNode);
            }
        },
        /**
         * 删除空节点
         */
        delEmptyNode: function (node) {
            if (this.checkIsEmpty(node)) {
                this.removeNode(node);
                return true;
            } else {
                return false;
            }
            return false;
        },
        /**
         * 向上、向下合并节点
         */
        clearNode: function (range, node, type) {
            var p = this.mergePreNode(node);
            var n = this.mergeNextNode(p);
            return n;
        },
        /**
         * 删除节点
         */
        removeNode: function (node, delParents) {
            if (delParents) {
                if (node.id) {
                    return;
                }
                if (node.parentNode) {
                    if (node.parentNode.childNodes.length == 1)
                        this.removeNode(node.parentNode, delParents);
                }
            }
            var rnode;
            if (node.previousSibling) {
                rnode = node.previousSibling;
            } else {
                rnode = node.parentNode;
            }
            this.remove(node);
            return rnode;
        },
        /**
         * 删除所有子节点
         */
        removeAllChild: function (node) {
            while (node.firstChild) {
                this.removeNode(node.firstChild);
            }
        },
        /**
         * 删除空占位符 node:需要删除空占位符的节点 range:选区
         */
        removeSpace: function (node, range) {
            if (!node || node.nodeValue == constants.EMPTY) {
                return;
            }
            if (this.isElementNode(node)) {
                if (node.innerHTML == constants.SPACE) {
                    this.removeNode(node.firstChild, 1);
                } else {
                    node.innerHTML = node.innerHTML.replace(new RegExp(constants.SPACE), "");
                }
            } else {
                // 第一种情况是在当前文本节点后续进行进行标签创建，由于进行了替换，选区位置从最后一个变到第一个了，所以要重置选区
                if (range.startContainer == spaceNode) {
                    if (node.nodeValue == constants.SPACE) {
                        range.setStartAfter(node);
                        range.setEndAfter(node);
                        this.removeNode(node, 1);
                    } else {
                        node.nodeValue = node.nodeValue.replace(new RegExp(
                            constants.SPACE), "");
                        range.setStartAfter(node);
                        range.setEndAfter(node);
                    }
                } else {
                    if (node.nodeValue == constants.SPACE) {
                        this.removeNode(node, 1);
                        return;
                    }
                    // 这种情况是新加的标签不在原来的文本节点后面
                    node.nodeValue = node.nodeValue.replace(new RegExp(
                        constants.SPACE), "");
                }
            }
        },
        /**
         * 创建新的文本节点，内部存放一个空的占位符
         */
        createSpace: function (range) {
            this.removeSpace(spaceNode, range);
            spaceNode = this.createTextNode(constants.SPACE);
            return spaceNode;
        },
        /**
         * 创建一个空节点
         */
        createEmptyNode: function (name) {
            var enterNode = this.createCustomNode(name);
            enterNode.appendChild(this.createCustomNode(constants.BR));
            return enterNode;
        },
        /**
         * 创建一个文本节点
         *
         * @returns
         */
        createTextNode: function (text) {
            return document.createTextNode(text);
        },
        /**
         * 按传入的名称创建一个新的节点
         *
         * @param name
         *            需要创建的几点名字
         */
        createCustomNode: function (name) {
            return document.createElement(name);
        },
        /**
         * 若给予的节点为空，则进行删除，并判断父节点，如果父节点也是空， 则删除，依次往外推（节点中包含空格则不算空节点） 直到循环到最外层的空节点
         * flag:true 不向上连删除 false 向上连删
         */
        delEmptyNode: function (node, flag) {
            if (this.checkIsEmpty(node)) {
                var tempNode = node.parentNode;
                this.removeNode(node);
                if (!flag) {
                    this.delEmptyNode(tempNode, flag);
                }
                return true;
            }
        },
        /**
         * 若div标签内容的最后一个节点是br标签，那么进行删除 node:需要处理的节点
         */
        delLastBrNode: function (node) {
            var sparentNode;
            for (var i in parentIncludeNode) {
                sparentNode = this.getSpecalParentNode(parentIncludeNode[i],
                    node);
                if (sparentNode) {
                    break;
                }
            }
            // 如果最后一个节点是br标签，那么进行删除
            if (this.isBrLabel(sparentNode.lastChild)) {
                this.removeNode(sparentNode.lastChild);
            }
        },
        /**
         * 比较两节点是否相同，比较的是节点样式、class是否相同
         *
         * @param sNode
         * @param eNode
         * @returns {Boolean}
         */
        sameNodeCompare: function (sNode, eNode) {
            if (sNode.style.cssText || eNode.style.cssText) {
                var far = this.trim(sNode.style.cssText).split(";");
                var sar = this.trim(eNode.style.cssText).split(";");
                if (far.length != sar.length) {
                    return false;
                } else {
                    for (var i = 0; i < far.length; i++) {
                        if (this.indexOf(sar, far[i]) < 0) {
                            return false;
                        }
                    }
                }
            }
            if (sNode.classList.length > 0 || eNode.classList.length > 0) {
                if (sNode.classList.length != eNode.classList.length) {
                    return false;
                } else {
                    for (var i = 0; i < sNode.classList.length; i++) {
                        if (this.indexOf(eNode.classList, sNode.classList[i]) < 0) {
                            return false;
                        }
                    }
                }
            }
            return true;
        },
        /**
         * 判断后一个节点样式是否包含前一个节点的样式
         *
         * @param sNode
         *            原来的节点
         * @param eNode
         *            需要添加进去的节点
         * @returns {Boolean}
         */
        checkParentContainsChild: function (sNode, eNode) {
            if (sNode.nodeType == 3 || eNode.nodeType == 3) {
                return false;
            }
            if (sNode.id || eNode.id) {
                return false;
            }
            if (sNode.style.cssText || eNode.style.cssText) {
                var far = this.trim(sNode.style.cssText).split(";");
                var temp, far1 = [];
                for (var i = 0; i < far.length; i++) {
                    temp = far[i].split(":");
                    if (temp.length > 1) {
                        far1.push(this.trim(temp[0]));
                    }
                }
                far = far1;
                for (var i = 0; i < far.length; i++) {
                    if (eNode.style[far[i]] != sNode.style[far[i]]) {
                        return false;
                    }
                }
            }
            if (sNode.classList.length > 0 || eNode.classList.length > 0) {
                if (sNode.classList.length > eNode.classList.length) {
                    return false;
                } else {
                    for (var i = 0; i < sNode.classList.length; i++) {
                        if (this.indexOf(eNode.classList, sNode.classList[i]) < 0) {
                            return false;
                        }
                    }
                }
            }
            return true;
        },
        /**
         * 判断后一个节点中的样式是否包含前一个节点的样式名相同的名称
         */
        checkParentContainsSameStyName: function (sNode, eNode) {
            if (sNode.nodeType == 3 || eNode.nodeType == 3) {
                return false;
            }
            if (sNode.id || eNode.id) {
                return false;
            }
            if (sNode.style.cssText || eNode.style.cssText) {
                var far = this.trim(sNode.style.cssText).split(";");
                var temp, far1 = [];
                for (var i = 0; i < far.length; i++) {
                    temp = far[i].split(":");
                    if (temp.length > 1) {
                        far1.push(this.trim(temp[0]));
                    }
                }
                far = far1;
                for (var i = 0; i < far.length; i++) {
                    if (eNode.style[far[i]] != null) {
                        return true;
                    }
                }
            }
            return false;
        },
        /**
         * 检测节点的是否包含样式和类
         */
        checkNodeContainsStyleAndClass: function (node, nodeClass, nodeStyle) {
            if (nodeStyle.length == 0) {
                return true;
            }
            for (var st in nodeStyle) {
                if (node.style[st] != nodeStyle[st]) {
                    return false;
                }
            }
            if (nodeClass.length == 0) {
                return true;
            }
            if (node.classList.length > 0) {
                for (var i = 0; i < nodeClass.length; i++) {
                    if (this.indexOf(node.classList, nodeClass[i]) < 0) {
                        return false;
                    }
                }
            }
            return true;
        },
        /**
         * 处理节点中与当前节点标签相同的子节点， 若存在相同子节点标签，那么将子节点中的内容移到节点外面 btnNode:最外层节点
         */
        dealChildSameNode: function (btnNode) {
            var checkNodeList = btnNode.getElementsByTagName(btnNode.tagName);
            var dealNodeList = [];
            for (var i = 0; i < checkNodeList.length; i++) {
                if (this.checkParentContainsChild(checkNodeList[i], btnNode)) {
                    dealNodeList.push(checkNodeList[i]);
                }
            }
            // 进行节点内容移动到外面
            for (var i = 0; i < dealNodeList.length; i++) {
                this.moveAllChildNodeToOut(dealNodeList[i])
            }
        },
        /**
         * 将后一个节点的样式赋予到第一个节点中
         */
        setNodeStyle: function (fNode, sNode) {
            var cssText = sNode.style.cssText;
            if (this.trim(cssText) == constants.EMPTY) {
                return;
            }
            var far = this.trim(cssText).split(";");
            var temp;
            for (var i = 0; i < far.length; i++) {
                temp = far[i].split(":");
                if (temp.length > 1) {
                    fNode.style[this.trim(temp[0])] = this.trim(temp[1]);
                }
            }
        },
        /**
         * 删除前一个节点中与后一个节点样式名字相同的样式
         */
        delSameStyleName: function (fNode, sNode) {
            var cssText = sNode.style.cssText;
            if (this.trim(cssText) == constants.EMPTY) {
                return;
            }
            var far = this.trim(cssText).split(";");
            var temp;
            for (var i = 0; i < far.length; i++) {
                temp = far[i].split(":");
                if (temp.length > 1) {
                    fNode.style[this.trim(temp[0])] = null;
                }
            }
        },
        /**
         * 后一个节点的样式通过排他（前一个节点存在则不赋值）的方式赋予到前一个节点中
         */
        setUnNodeStyle: function (fNode, sNode) {
            var cssText = sNode.style.cssText;
            if (this.trim(cssText) == constants.EMPTY) {
                return;
            }
            var far = this.trim(cssText).split(";");
            var temp, stName;
            for (var i = 0; i < far.length; i++) {
                temp = far[i].split(":");
                if (temp.length > 1) {
                    stName = this.trim(temp[0]);
                    if (this.checkStrIsEmpty(fNode.style[stName])) {
                        fNode.style[stName] = this.trim(temp[1]);
                    }
                }
            }
        },
        /**
         * 检测str是否为空
         */
        checkStrIsEmpty: function (str) {
            if (!str) {
                return true;
            }
            if (str == constants.EMPTY) {
                return true;
            }
        },
        /**
         * 判断节点是否含有样式
         *
         * @param node
         * @returns {Boolean}
         */
        checkHasNodeStyle: function (node) {
            if (node.getAttribute("style")) {
                return true;
            }
            if (node.getAttribute("class")) {
                return true;
            }
        },
        /**
         * 判断是否是允许合并的节点 判断方式:1、标签名字相同 2、所含样式是否相同 3、所含类是否相同
         */
        checkIsMergeLabel: function (sNode, eNode) {
            if (!sNode || !eNode) {
                return false;
            }
            if (sNode.id || eNode.id) {
                return false;
            }
            if (sNode.tagName != eNode.tagName) {
                return false;
            }
            return this.sameNodeCompare(sNode, eNode);
        },
        /**
         * 判断是否是空节点 node: 进行判断的节点
         */
        checkIsEmpty: function (node) {
            if (!node) {
                return false;
            }
            // 可忽略节点不进行校验
            if (this.checkIsIgnoreNode(node)) {
                return false;
            }
            if (node.tagName == constants.TD) {
                return false;
            }
            var pv;
            if (this.isElementNode(node)) {
                pv = node.innerHTML;
            } else {
                pv = node.data;
            }
            if (pv == constants.EMPTY) {
                return true;
            } else {
                return false;
            }
        },
        /**
         * 检测是否是空节点，检测的情况包括所有类型，内容为空，空字符，回车标签
         */
        checkEmptyIncludeAll: function (node) {
            if (!node) {
                return false;
            }
            // 可忽略节点不进行校验
            if (this.checkIsIgnoreNode(node)) {
                return false;
            }
            if (node.tagName == constants.TD) {
                return false;
            }
            var hv;
            if (this.isElementNode(node)) {
                hv = node.innerHTML;
            } else {
                hv = node.data;
            }
            if (hv == constants.EMPTY || hv == constants.SPACE || hv == "<br>") {
                return true;
            } else {
                return false;
            }
        },
        /**
         * 检测节点中是否包含有文本节点 node:检测的节点 举例：
         * <li><strong></strong></li>
         * <li><strong>\u200B</strong></li>
         * <li><strong><br>
         * </strong></li>
         * <li><strong><span><label></label></span></strong></li>
         * 等皆为空的节点，没有文本节点
         */
        checkHasTextNode: function (node) {
            if (this.checkIsEmpty(node)) {
                return false;
            } else {
                var hasText = false;
                this.forListNode(node.firstChild, node, function (node) {
                    if (!hasText) {
                        if (node.wholeText.length > 0) {
                            hasText = true;
                        }
                    }
                    return node;
                }, 3);
                return hasText;
            }
        },

        /**
         * 获取下一个不是br标签的节点 node:当前节点作为基点
         */
        getNextNotBRNode: function (node) {
            var time = 1;
            /*
			 * 如果当前节点是br节点，那么获取下一个节点
			 */
            while (this.isBrLabel(node)) {
                node = node.nextSibling;
                if (++time > 1000) {
                    return;
                }
                if (!node) {
                    return;
                }
            }
            return node;
        },
        /**
         * 删除父节点下所有为空的子孙节点
         * delFlag:是否删除空的父标签
         */
        deleteAllChildrenEmptyNodes: function (node, delFlag) {
            if (!node) {
                return;
            }
            if (delFlag && this.checkIsEmpty(node)) {
                this.removeNode(node);
                return;
            }
            var tempNode = null;
            var parentNode = node;
            node = node.firstChild;
            while (node) {
                if (this.checkIsEmpty(node)) {
                    if (this.isOutNodeCheck(node)) {
                        this.removeNode(node);
                        break;
                    }
                    // 节点为空时，如果右边还有节点还有节点那么检测右边节点，否则如果左边节点不存在说明当前节点删除后当前节点的父节点可能也要删除
                    if (node.nextSibling) {
                        tempNode = node.nextSibling;
                    } else if (!node.previousSibling) {
                        tempNode = node.parentNode;
                    } else {
                        tempNode = null;
                    }
                    this.removeNode(node);
                    if (tempNode) {
                        node = tempNode;
                        continue;
                    } else {
                        break;
                    }
                }
                if (!(this.isTextNode(node) || this.checkIsIgnoreNode(node))) {
                    // 不是空节点的元素节点，肯定存在第一个子节点
                    this.deleteAllChildrenEmptyNodes(node.firstChild, delFlag);
                }
                // 检测节点不为空时，进行横向移动到下一个节点
                if (node.nextSibling) {
                    node = node.nextSibling;
                    if (this.checkIsEmpty(node.previousSibling)) {
                        this.removeNode(node.previousSibling);
                    }
                } else {
                    if (this.checkIsEmpty(node)) {
                        this.removeNode(node);
                    }
                    break;
                }
            }
            //子标签删除完后，父标签可能为空
            if (delFlag && this.checkIsEmpty(parentNode)) {
                this.removeNode(parentNode);
            }
        },
        /**
         * 空节点中添加br标签 nodeList:节点集合
         */
        emptyNodeListAddBr: function (nodeList) {
            for (var i = 0; i < nodeList.length; i++) {
                this.emptyNodeAddBr(nodeList[i], this
                    .createCustomNode(constants.BR));
            }
        },
        /**
         * 空节点中添加指定标签
         */
        emptyNodeAddBr: function (node, addNode) {
            // 如果node节点是空的那么添加br节点
            if (this.checkStrIsEmpty(this.trim(node.innerHTML))) {
                node.innerHTML = constants.EMPTY;
                node.appendChild(addNode.cloneNode());
            }
        },
        /**
         * 判断是否是空文本节点 node:判断的节点
         */
        isEmptyTextNode: function (node) {
            if (node.nodeType == 3 && node.nodeValue == constants.EMPTY) {
                return true;
            }
            return false;
        },
        /**
         * 寻找前一个不为br标签的节点 node:寻找点的开始节点
         */
        getPreNotBrTag: function (node) {
            while (this.isBrLabel(node)) {
                node = node.previousSibling;
            }
            return node;
        },
        /**
         * 寻找后一个不为br标签的节点 node:寻找点的开始节点
         */
        getNextNotBrTag: function (node) {
            while (this.isBrLabel(node)) {
                node = node.nextSibling;
            }
            return node;
        },
        /**
         * 判断是否是br标签 node：判断的标签节点
         */
        isBrLabel: function (node) {
            if (!node) {
                return false;
            }
            if (node.tagName == constants.BR) {
                return true;
            } else {
                return false;
            }
        },
        /**
         * 判断是否是填充的字符 node：判断的节点
         */
        isFillChar: function (node) {
            if (!this.isTextNode(node))
                return false;
            if (node.nodeValue == constants.SPACE) {
                return true;
            }
            return false;
        },
        /**
         * 判断是否是文本节点 node:判断的节点
         */
        isTextNode: function (node) {
            if (node.nodeType == 3) {
                return true;
            } else {
                return false;
            }
        },
        /**
         * 判断是否为元素节点 node:判断的节点
         */
        isElementNode: function (node) {
            if (!node) {
                return false;
            }
            if (node.nodeType == 1) {
                return true;
            } else {
                return false;
            }
        },
        /**
         * 处理连续的内部相同标签节点
         */
        dealInnerSerisSameTagNameNode: function (pNode, cNode) {
            if (!this.isElementNode(pNode)) {
                return;
            }
            if (!this.isElementNode(cNode)) {
                return;
            }
            if (this.isSingleNodeCheck(cNode)) {
                return;
            }
            if (cNode.firstChild != cNode.lastChild) {
                return;
            }
            if (pNode.tagName == cNode.firstChild.tagName) {
                if (cNode.firstChild.getAttribute("style")) {
                    this.setUnNodeStyle(pNode, cNode.firstChild);
                    this.dealInnerSerisSameTagNameNode(pNode, cNode.firstChild);
                }
                this.moveAllChildNodeToOut(cNode.firstChild);
            } else {
                this.dealInnerSerisSameTagNameNode(pNode, cNode.firstChild);
            }
        },
        /**
         * 判断node是否是pNode连续的内部节点
         */
        checkIsSerisNodeChild: function (pNode, node, range, type) {
            if (pNode && node) {
                if (type == 1) {
                    if (range.startOffset != 0) {
                        return false;
                    }
                } else {
                    if (node.nodeType == 3) {
                        if (range.endOffset != node.length) {
                            return false;
                        }
                    } else {
                        if (range.endOffset != node.childNodes.length) {
                            return false;
                        }
                    }
                }
                var tempNode;
                while (pNode != node) {
                    if (node.id) {
                        return false;
                    }
                    if (type == 1) {
                        tempNode = node.previousSibling;
                    } else {
                        tempNode = node.nextSibling;
                    }
                    if (tempNode) {
                        return false;
                    }
                    node = node.parentNode;
                }
                return true;
            }
            return false;
        },
        /**
         * 检测选区开始是否包含全节点
         *
         * @param range
         * @returns {Boolean}
         */
        checkSincloudAll: function (range) {
            if (range.startOffset == 0) {
                return true;
            }
            if (range.startContainer.childNodes.length > 0) {
                if (this
                    .isElementNode(range.startContainer.childNodes[range.startOffset])) {
                    return true;
                }
            }
        },
        /**
         * 检测选区结束是否包含全节点
         *
         * @param range
         */
        checkEincloudAll: function (range) {
            if (range.endOffset == range.endContainer.length) {
                return true;
            }
            if (range.endContainer.childNodes.length > 0) {
                if (range.endContainer.childNodes.length == range.endOffset) {
                    return true;
                }
                if (range.endContainer.childNodes.length == 1) {
                    return true;
                }
                if (range.endOffset > 0) {
                    if (this
                        .isElementNode(range.endContainer.childNodes[range.endOffset].previousSibling)) {
                        return true;
                    }
                }
            }
        },
        /**
         * 检测是否是序列节点
         */
        checkIsList: function (node) {
            if (node.tagName == constants.OL || node.tagName == constants.UL) {
                return true;
            } else {
                return false;
            }
        },
        /**
         * 检测节点是否是table或ol或ul
         */
        checkIstouNode: function (node) {
            if (!node) {
                return false;
            }
            if (node.tagName == constants.TABLE || node.tagName == constants.OL
                || node.tagName == constants.UL
                || node.tagName == constants.PRE) {
                return true;
            } else {
                return false;
            }
        },
        /**
         * 比较两个数组内容是否相同
         */
        compareAddr: function (indexA, indexB) {
            if (indexA.length != indexB.length)
                return 0;
            for (var i = 0, l = indexA.length; i < l; i++) {
                if (indexA[i] != indexB[i])
                    return 0
            }
            return 1;
        },
        /**
         * 比较两个地址是否相同
         */
        compareRangeAddress: function (rngAddrA, rngAddrB) {
            if (rngAddrA.collapsed != rngAddrB.collapsed) {
                return 0;
            }
            if (rngAddrA.startOffset != rngAddrB.startOffset) {
                return 0;
            }
            if (rngAddrA.endOffset != rngAddrB.endOffset) {
                return 0;
            }
            if (!this.compareAddr(rngAddrA.startNode, rngAddrB.startNode)
                || !this.compareAddr(rngAddrA.endNode, rngAddrB.endNode)) {
                return 0;
            }
            return 1;
        },

        /**
         * 判断是否是可忽略的节点 node:判断的节点
         */
        checkIsIgnoreNode: function (node) {
            if (this.indexOf(ignoreNode, node.tagName) > -1) {
                return true;
            } else {
                return false;
            }
        },
        /**
         * 检测是否是pre节点
         */
        checkIsPreNode: function (node) {
            if (constants.PRE == node.tagName) {
                return true;
            } else {
                return false;
            }
        },
        /**
         * 获取表格内td或tr的位置
         */
        getTableNodeIndex: function (node) {
            var index = 0;
            while (node = node.previousSibling) {
                ++index;
            }
            return index;
        },
        /**
         * 获取二维数组中，对应节点的位置 node:获取的节点 array:查找的数组 type:查找的类型 0：前 1：后
         */
        getArrayIndex: function (node, array, type) {
            for (var i = 0; i < array.length; i++) {
                var j = 0;
                while (array[i][j]) {
                    if (node == array[i][j].td) {
                        var cl = array[i][j].td.colSpan;
                        var rl = array[i][j].td.rowSpan;
                        if (type) {
                            if (cl > 1) {
                                j = j + cl - 1;
                            }
                            if (rl > 1) {
                                i = i + rl - 1;
                            }
                        }
                        return i + "," + j
                    }
                    j++;
                }
            }
            return;
        },
        /**
         * 获取最外层父节点，返回父节点
         */
        getNodeOutNode: function (node) {
            var sparentNode;
            for (var i in parentIncludeNode) {
                sparentNode = this.getSpecalParentNode(parentIncludeNode[i],
                    node);
                if (sparentNode) {
                    return sparentNode;
                }
            }
        },
        /**
         * 获取所有最外层的父节点如 div、h1 外置节点，返回父节点集合
         */
        getOutNode: function (range) {
            var sNode = range.startContainer;
            var parentArray = [];
            var sparentNode = this.getNodeOutNode(sNode);
            var eparentNode = this.getNodeOutNode(range.endContainer);
            if (!sparentNode && sNode.id) {
                sparentNode = sNode.childNodes[range.startOffset];
            }
            if (sparentNode == eparentNode) {
                parentArray.push(sparentNode);
                return parentArray;
            }
            // 如果不是选区，那么不用循环查询后面的外置节点
            if (!range.collapsed) {
                if (!eparentNode && range.endContainer.id) {
                    eparentNode = range.endContainer.childNodes[range.endOffset - 1];
                }
                var tempNode = sparentNode;
                while (tempNode) {
                    tempNode = sparentNode.nextSibling;
                    parentArray.push(sparentNode);
                    if (tempNode != eparentNode) {
                        sparentNode = tempNode;
                    } else {
                        parentArray.push(eparentNode);
                        break;
                    }
                }
            } else {
                parentArray.push(sparentNode);
            }
            return parentArray;
        },
        /**
         * 对象合并，将后一个对象合并到前一个对象中
         */
        assign: function (sNode, eNode) {
            if (Object.assign) {
                Object.assign(sNode, eNode);
            } else {
                for (var i in eNode) {
                    sNode[i] = eNode[i];
                }
            }
        },
        /**
         * 剪切方法重写
         */
        extractContents: function (range, flag) {
            if (range.startOffset == 0) {
                if (this.checkIsTextNode(range.startContainer)) {
                    range.setStartBefore(range.startContainer);
                }
                if (!flag) {
                    if (range.startContainer.nodeType == 1) {
                        if ((!range.startContainer.contains(range.endContainer)) &&
                            (range.startContainer != range.endContainer)) {
                            range.setStartBefore(range.startContainer);
                        }
                    }
                }
            }
            if (range.endOffset == range.endContainer.length) {
                if (this.checkIsTextNode(range.endContainer)) {
                    range.setEndAfter(range.endContainer);
                }
                if (!flag) {
                    if ((!range.endContainer.contains(range.startContainer)) &&
                        (range.startContainer != range.endContainer)) {
                        range.setEndAfter(range.endContainer);
                    }
                }
            }
            return range.extractContents();
        },
        /**
         * 以什么开始 node:检测的节点 text:检测的文本
         */
        startsWith: function (node, text) {
            var reg = new RegExp("^" + text);
            return reg.test(node);
        },
        /**
         * 以什么结尾 node:检测的节点 text:检测的文本
         */
        endsWith: function (node, text) {
            var reg = new RegExp(text + "$");
            return reg.test(node);
        },
        /**
         * 判断当前节点是否是外标签节点 node：判断的节点
         */
        isOutNodeCheck: function (node) {
            if (this.indexOf(parentIncludeNode, node.tagName) > -1) {
                return true;
            } else {
                return false;
            }
        },
        /**
         * 判断当前节点类型是否是内标签节点 node:判断的节点
         */
        ischildNodeCheck: function (node) {
            if (this.indexOf(childIncludeNode, node.tagName) > -1) {
                return true;
            } else {
                return false;
            }
        },
        /**
         * 判断是否是单标签节点 node：判断的节点
         */
        isSingleNodeCheck: function (node) {
            if (this.indexOf(singleNode, node.tagName) > -1) {
                return true;
            } else {
                return false;
            }
        },
        /**
         * 进行url编码转义，目前转义的字符为 % & +
         *
         * @param text
         * @returns
         */
        tranWord: function (text) {
            return text.replace(/%/g, "%25").replace(/\+/g, "%2B").replace(
                /&/g, "%26");
        },
        /**
         * 获取选区开始最小的节点
         */
        getFirstStartMinNode: function (range) {
            var startNode = range.startContainer;
            if (startNode.nodeType == 3) {
                return startNode;
            } else {
                if (range.startOffset == 0 && startNode.childNodes.length > 0) {
                    var minNode = this
                        .getMinNode(startNode.childNodes[range.startOffset]);
                    if (minNode != startNode) {
                        range.setStartBefore(minNode);
                        return minNode;
                    } else {
                        return minNode;
                    }
                } else {
                    return startNode;
                }
            }
        },
        /**
         * 获取选区结束最小的节点
         */
        getLastEndMinNode: function (range) {
            var endNode = range.endContainer;
            if (endNode.nodeType == 3) {
                return endNode;
            } else {
                if (endNode.childNodes.length > 0
                    && range.endOffset == endNode.childNodes.length) {
                    var minNode = this
                        .getMinLastNode(endNode.childNodes[range.endOffset - 1]);
                    if (minNode != endNode) {
                        range.setEndAfter(minNode);
                        return minNode.parentNode;
                    } else {
                        return minNode;
                    }
                } else {
                    return endNode;
                }
            }
        },
        /**
         * 检测是否是有用的按键
         */
        checkIsUseFulKeyCode: function (keyCode) {
            if (keyCode >= 37 && keyCode <= 46 || keyCode >= 48
                && keyCode <= 57 || keyCode >= 65 && keyCode <= 90
                || keyCode >= 96 && keyCode <= 111 || keyCode >= 186
                && keyCode <= 222 || {
                    13: 1, 8: 1, 46: 1, 9: 1, 32: 1, 229: 1, 16: 1, 17: 1, 91: 1
                }[keyCode]) {
                // 229中文输入,32空格，9tab， 46删除，8后退，13回车 17 control 91 command 16 shift
                return true;
            } else {
                return false;
            }
        },
        /**
         * 检测是否是英文或数字或特殊字符的按键
         */
        checkIsValueKeyCode: function (keyCode) {
            if (keyCode >= 48 && keyCode <= 57 || keyCode >= 65
                && keyCode <= 90 || keyCode >= 96 && keyCode <= 111
                || keyCode >= 186 && keyCode <= 222) {
                return true;
            } else {
                return false;
            }
        },
        /**
         * 检测是否是需要加入历史记录的按键
         */
        checkHisKeyCode: function (keyCode) {
            if (keyCode >= 37 && keyCode <= 46 || keyCode >= 48
                && keyCode <= 57 || keyCode >= 65 && keyCode <= 90
                || keyCode >= 96 && keyCode <= 111 || keyCode >= 186
                && keyCode <= 222 || {
                    13: 1, 8: 1, 46: 1, 9: 1, 32: 1
                }[keyCode]) {
                // 229中文输入,32空格，9tab， 46删除，8后退，13回车 17 control 91 command
                return true;
            } else {
                return false;
            }
        },
        /**
         * 检测节点中是否只有br标签
         */
        checkIsOnlyBr: function (node) {
            if (node.childNodes.length != 1) {
                return false;
            }
            if (this.isBrLabel(node.firstChild)) {
                return true;
            }
        },
        /**
         * 获取对象的class中的样式
         *
         * @param obj
         * @param attr
         */
        getStyle: function (obj, attr) {
            var ie = !+"\v1";// 简单判断ie6~8
            if (attr == "backgroundPosition") {// IE6~8不兼容backgroundPosition写法，识别backgroundPositionX/Y
                if (ie) {
                    return obj.currentStyle.backgroundPositionX + " "
                        + obj.currentStyle.backgroundPositionY;
                }
            }
            if (obj.currentStyle) {
                return obj.currentStyle[attr];
            } else {
                return document.defaultView.getComputedStyle(obj, null)[attr];
            }
        },
        /**
         * 获取图片位置，因为offsetTop只是针对它上层对象的位置， 所以要循环到选框相同上层对象的位置为止
         */
        getPlace: function (node, seleNode) {
            var x = 0, y = 0;
            while (seleNode.offsetParent.contains(node)) {
                y += node.offsetTop;
                x += node.offsetLeft;
                node = node.offsetParent;
                if (node == seleNode.offsetParent) {
                    break;
                }
            }
            return {
                'offsetLeft': x,
                'offsetTop': y
            };
        },
        /**
         * 获取节点位置
         * @param element
         * @returns
         */
        getXY: function (element) {
            var x = 0, y = 0;
            while (element.offsetParent) {
                y += element.offsetTop;
                x += element.offsetLeft;
                element = element.offsetParent;
            }
            return {'offsetLeft': x, 'offsetTop': y};
        },
        /**
         * 删除选区
         */
        deleteContents: function (range) {
        	var stflag = true;
        	var edflag = true;
            if (range.startContainer.nodeType == 3 && range.startOffset == 0) {
                range.setStartBefore(range.startContainer);
                //开始状态标志，避免改了之后又满足下面的条件
                stflag = false;
            }
            if (range.endContainer.nodeType == 3 && range.endOffset == range.endContainer.length) {
                range.setEndAfter(range.endContainer);
                //结束状态标志，避免改了之后又满足下面的条件
                edflag = false;
            }
            if (stflag&&range.startContainer.nodeType == 1 && range.startOffset == 0) {
                range.setStartBefore(range.startContainer);
            }
            if (edflag&&range.endContainer.nodeType == 1 && range.endOffset == range.endContainer.childNodes.length) {
                range.setEndAfter(range.endContainer);
            }
            range.deleteContents();
        },
        /**
         * 当选区在外置节点之间，那么要进行重新设定节点才行
         */
        resetParentNodeNewRange: function (node, range) {
            // 如果选区删除后，光标位于父标签之间那么要进行选区重置
            if (node.id) {
                var spnode, epnode;
                if (range.startOffset > 0) {
                    spnode = range.startContainer.childNodes[range.startOffset - 1];
                    if (this.checkIsEmpty(spnode)) {
                        spnode.appendChild(this.createCustomNode(constants.BR));
                    }
                    if (this.checkIsEmpty(spnode.nextSibling)) {
                        this.remove(spnode.nextSibling);
                    }
                    var s = this.getMinLastNode(spnode);
                    if (s) {
                        if (this.isBrLabel(s)) {
                            range.setStartBefore(s);
                        } else {
                            range.setStartAfter(s);
                        }
                        range.collapse(true);
                        return;
                    }
                }
                epnode = range.startContainer.childNodes[range.startOffset];
                if (this.checkIsEmpty(epnode)) {
                    epnode.appendChild(this.createCustomNode(constants.BR));
                }
                var e = this.getMinNode(epnode);
                range.setStartBefore(e);
                range.collapse(true);
                return;
            }
        },
        /**
         * 根据样式名获取所有子节点（ie兼容）
         *
         * @param node
         * @param searchClass
         * @returns
         */
        getElementsByClassName: function (node, searchClass) {
            if (document.getElementsByClassName) {
                //将HTMLCollection集合转换成数组类型（保持上下一致，HTMLCollection集合是动态的 移除后长度会减少，数组是不变的，不保持一致的话for循环不好写）
                return Array.prototype.slice.call(node.getElementsByClassName(searchClass), 0);
            } else {
                var elements = node.getElementsByTagName("*"), result = [], pattern, current, match;
                pattern = new RegExp("(^|\\s+)" + searchClass + "(\\s+|$)");
                var j = elements.length;
                while (--j >= 0) {
                    current = elements[j];
                    match = false;
                    match = pattern.test(current.className);
                    if (match)
                        result.push(current);
                }
                return result;
            }
        },
        /**
         * 节点删除（ie兼容）
         *
         * @param node
         * @returns
         */
        remove: function (node) {
            var pnode, type;
            if (node.nextSibling) {
                pnode = node.nextSibling;
                type = 1;
            } else {
                pnode = node.parentNode;
                type = 2;
            }
            if (node.remove) {
                node.remove();
            } else {
                var parent = node.parentNode
                if (parent) {
                    parent.removeChild(node);
                }
            }
            if (type == 1) {
                this.clearEmptyTextNode(pnode.previousSibling);
            } else {
                if (pnode&&pnode.lastChild) {
                    this.clearEmptyTextNode(pnode.lastChild);
                }
            }
            return node;
        },
        /**
         * 清除空节点
         */
        clearEmptyTextNode: function (node) {
            if (!node) {
                return;
            }
            var tempNode = node;
            while (tempNode) {
                node = tempNode;
                tempNode = node.previousSibling;
                if (node.nodeType == 3 && node.data == constants.EMPTY) {
                    node.parentNode.removeChild(node);
                }
            }
        },
        /**
         * 获取具体的位置
         *
         * @param array
         * @param item
         * @returns {Number}
         */
        indexOf: function (array, item) {
            var index = -1;
            for (var i = 0; i < array.length; i++) {
                if (array[i] == item) {
                    index = i;
                    break;
                }
            }
            return index;
        },
        getPasteImage: function (e) {
            if (e.clipboardData && e.clipboardData.items && e.clipboardData.items.length > 0) {
                var imgs = [];
                for (var i = 0; i < e.clipboardData.items.length; i++) {
                    if (/^image\//.test(e.clipboardData.items[i].type)) {
                        imgs.push(e.clipboardData.items[i]);
                    }
                }
                return imgs;
            } else {
                return null;
            }
        },
        getRangeStartText: function(range){
        	if (range.startContainer.childNodes.length == range.startOffset) {
        		return range.startContainer.childNodes[range.startOffset-1];
        	}else{
        		return range.startContainer.childNodes[range.startOffset];
        	}
        }
    };

    /**
     * 类似于常量类njqEditor.constants
     */
    var constants = window.njqEditor.constants = {
        // 类似隐藏的空格用于定位
        SPACE: "\u200B",
        BR: "BR",
        BODY: "BODY",
        EMPTY: "",
        DIV: "DIV",
        P: "P",
        SPAN: "SPAN",
        LABEL: "label",
        PRE: "PRE",
        A: "A",
        LI: "LI",
        TABLE: "TABLE",
        TR: "TR",
        TD: "TD",
        TH: "TH",
        IMG: "IMG",
        INPUT: "INPUT",
        HR: "HR",
        OL: "OL",
        UL: "UL",
        STYLE: "STYLE",
        BOOKMARK: "BOOKMARK"// 书签节点，用于隔离需要重置选区的内容
    };
})();
