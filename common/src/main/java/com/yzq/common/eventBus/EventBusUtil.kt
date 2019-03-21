package com.yzq.common.eventBus

import org.greenrobot.eventbus.EventBus



 /**
 * @description: EventBus封装
 * @author : yzq
 * @date   : 2018/7/9
 * @time   : 11:20
 *
 */

class EventBusUtil private constructor() {

    companion object {
        /*
        * 注册
        * */
        fun register(subscriber: Any) {
            if (!EventBus.getDefault().isRegistered(subscriber)) {
                EventBus.getDefault().register(subscriber)
            }
        }


        /*
        * 取消注册
        * */
        fun unregister(subscriber: Any) {

            if (EventBus.getDefault().isRegistered(subscriber)) {
                EventBus.getDefault().unregister(subscriber)
            }

        }

        /*发送消息*/
        fun post(eventMsg: EventMsg) {
            EventBus.getDefault().post(eventMsg)
        }

        /**
         * 发布粘性订阅事件
         *
         * @param event 事件对象
         */
        fun postSticky(eventMsg: EventMsg) {
            EventBus.getDefault().postSticky(eventMsg)
        }

        /**
         * 移除指定的粘性订阅事件
         *
         * @param eventType class的字节码，例如：String.class
         */
        fun <T> removeStickyEvent(eventType: Class<T>) {
            val stickyEvent = EventBus.getDefault().getStickyEvent(eventType)
            if (stickyEvent != null) {
                EventBus.getDefault().removeStickyEvent(stickyEvent)
            }
        }

        /**
         * 移除所有的粘性订阅事件
         */
        fun removeAllStickyEvents() {
            EventBus.getDefault().removeAllStickyEvents()
        }

        /**
         * 取消事件传送
         *
         * @param event 事件对象
         */
        fun cancelEventDelivery(event: Any) {
            EventBus.getDefault().cancelEventDelivery(event)
        }

    }

}