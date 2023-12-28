import NotImplementedError from "/js/error/NotImplementedError.js";

export default class AbstractTreeNode {
    hasUpper() {
        throw new NotImplementedError('hasUpper');
    }
    hasChildren() {
        throw new NotImplementedError('hasChildren');
    }

    getChildren() {
        throw new NotImplementedError('getChildren');
    }
    getUpperId() {
        throw new NotImplementedError('getUpperId');
    }
}
