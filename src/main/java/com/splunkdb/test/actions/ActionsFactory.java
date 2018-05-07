package com.splunkdb.test.actions;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import com.splunkdb.test.beans.ResponseInfo;
import com.splunkdb.test.actions.AbstractAPIActions;
import org.springframework.stereotype.Component;

@Component
public class ActionsFactory {

    @Autowired
    private ResponseInfo responseInfo;

    @SneakyThrows
    public <T extends AbstractAPIActions> T get(Class<T> clazz) {
        return (T) clazz.newInstance()
                .withResponseInformation(responseInfo)
                .withActionsFactory(this);
    }
}
