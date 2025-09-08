package com.xiaomi.wusheng.work_0223.question_1;

class ApiResponse{
    private boolean success;
    private Data data;

    public boolean isSuccess(){
        return success;
    }

    public void setSuccess(boolean success){
        this.success = success;
    }

    public Data getData(){
        return data;
    }

    public void setData(Data data){
        this.data = data;
    }
}
