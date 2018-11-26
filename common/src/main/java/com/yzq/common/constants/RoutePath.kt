package com.yzq.common.constants


/**
 * @description: 路由path
 * @author : yzq
 * @date   : 2018/10/30
 * @time   : 14:05
 *
 */

class RoutePath {

    class Main() {
        companion object {
            const val TASK = "/main/task"
            const val NEWS = "/main/news"
            const val IMG = "/main/img"
            const val DATE_TIME = "/main/dateTime"


        }
    }

    /*用户模块*/
    class User {
        companion object {
            const val LOGIN = "/user/login"
            const val USER = "/user/user"

        }
    }

    /*任务模块*/
    class Task {
        companion object {
            const val PROJECT = "/task/project"
            const val TASK = "/task/task"
            const val ASSESS_TASK = "/task/assessTask"
            const val ASSESS_TASK_ITEM = "/task/assessTaskItem"
            const val SINGLE_SELECT = "/task/singleSelect"
            const val MULTI_SELECT = "/task/multiSelect"
            const val ASSESS_TITLE = "/task/assessTitle"

        }
    }
}