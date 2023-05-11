package com.mszlu.service;

import com.mszlu.vo.Result;
import com.mszlu.vo.params.RegisterParam;

public interface RegisterService {
    /*注册*/
    Result register(RegisterParam registerParam);
}
