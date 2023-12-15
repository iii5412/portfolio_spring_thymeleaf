const GRID_FILED_TYPE = {
    TEXT: 'text',
    NUMBER: 'number',
    DATE: 'datetime',
    OBJECT: 'object',
}

const ROW_STATE = {
    CREATED_AND_DELETED: 'createAndDeleted',
    CREATED: 'created',
    DELETED: 'deleted',
    NONE: 'none',
    UPDATED: 'updated',
}

const COLUMN_TYPE = {
    DATA: 'data',
}

function createColumn(name, type, width) {
    return new Column(name, type, width);
}

function createField(fieldName) {
    return new Field(fieldName);
}


class Field {
    field = {};

    constructor(fieldName) {
        this.field.fieldName = fieldName;
    }

    text() {
        this.field.dataType = GRID_FILED_TYPE.TEXT;
        return this;
    }

    number() {
        this.field.dataType = GRID_FILED_TYPE.NUMBER;
        return this;
    }

    end() {
        return this.field;
    }
}


class Column {
    col = {};

    constructor(name, type, width) {
        const fieldName = name;
        Object.assign(this.col, {name, fieldName, type, width});
    }

    #addStyleName(styleName) {
        const styleNames = this.col.styleName ? this.col.styleName.split(' ') : [];

        if (!styleNames.find(_styleName => _styleName === styleName))
            styleNames.push(styleName);

        this.col.styleName = styleNames.join(' ');
        return this;
    }

    #addHeaderStyleName(styleName) {
        if (!this.col.hasOwnProperty('header'))
            return this;

        const styleNames = this.col.header.styleName ? this.col.header.styleName.split(' ') : [];

        if (!styleNames.find(_styleName => _styleName === styleName))
            styleNames.push(styleName);

        this.col.header.styleName = styleNames;
        return this;
    }

    removeStyleName(styleName) {
        const styleNames = this.col.styleName ? this.col.styleName.split(' ') : [];
        const filteredStyleNames = styleNames.filter(_styleName => _styleName !== styleName);
        this.col.styleName = filteredStyleNames.join(' ');
        return this;
    }

    alignLeft() {
        this.#addStyleName('left-column');
        return this;
    }

    editable(editable) {
        this.col.editable = editable;
        return this;
    }

    visible(visible) {
        this.col.visible = visible;
        return this;
    }

    header(text, options = {}) {
        this.col.header = {text};
        Object.assign(this.col.header, options);
        return this;
    }

    #isEditableColumn() {
        return !this.col.hasOwnProperty('editable') ||
            (this.col.hasOwnProperty('editable') && this.col.editable)
    }

    end() {
        if (this.#isEditableColumn()) {
            this.#addHeaderStyleName('editable-column');
        }
        return this.col;
    }
}


export {GRID_FILED_TYPE, createColumn, COLUMN_TYPE, ROW_STATE, createField}
