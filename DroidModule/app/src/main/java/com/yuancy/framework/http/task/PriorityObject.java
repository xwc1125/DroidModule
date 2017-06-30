package com.yuancy.framework.http.task;


 /**
  * 
  * <p>
  * Title: PriorityObject
  * </p>
  * <p>
  * Description: 优先级绑定
  * </p>
  * <p>
  * 
  * </p>
  * @author zhangqy
  * @date 2016年5月11日下午2:24:05
  * 
  * @param <E>
  */
public class PriorityObject<E> {

    public final Priority priority;
    public final E obj;

    public PriorityObject(Priority priority, E obj) {
        this.priority = priority == null ? Priority.DEFAULT : priority;
        this.obj = obj;
    }
}
