import AbstractTreeNode from "/js/common/AbstractTreeNode.js";

export default class FolderMenu extends AbstractTreeNode {

    constructor({id, menuName, upperId, subMenus}) {
        super();

        if(!subMenus)
            subMenus = [];

        Object.assign(this, {id, menuName, upperId, subMenus});
    }

    /**
     * @return {Number}
     */
    getId() {
        return this.id;
    }

    /**
     * @return {String}
     */
    getMenuName() {
        return this.menuName;
    }

    /**
     * @return {boolean}
     */
    hasUpper() {
        return !!this.upperId;
    }

    /**
     * @return {boolean}
     */
    hasChildren() {
        return this.subMenus.length > 0
    }

    /**
     * @return {FolderMenu[]}
     */
    getChildren() {
        return this.subMenus.map(sm => new FolderMenu(sm));
    }

    /**
     * @return {Number}
     */
    getUpperId() {
        return this.upperId;
    }

}
