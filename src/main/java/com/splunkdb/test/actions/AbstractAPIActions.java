package com.splunkdb.test.actions;

import com.splunkdb.test.beans.ResponseInfo;

public class AbstractAPIActions {
    protected ResponseInfo responseInfo;
    protected ActionsFactory actions;

    public AbstractAPIActions withResponseInformation(ResponseInfo info) {
        this.responseInfo = info;
        return this;
    }

    public AbstractAPIActions withActionsFactory(ActionsFactory actionsFactory) {
        this.actions = actionsFactory;
        return this;
    }
}
