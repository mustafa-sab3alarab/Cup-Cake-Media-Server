package com.the_chance.utils.errors

import com.the_chance.utils.*
import io.ktor.server.plugins.statuspages.*
import java.io.IOException

fun StatusPagesConfig.genericErrorsException() {

    exception<DeleteError> { call, _ ->
        call.badRequest("delete failed, check the id and try again.")
    }

    exception<CommentNotFoundError> { call, _ ->
        call.badRequest("Comment not found")
    }

    exception<NotUpdateCommentError> { call, _ ->
        call.badRequest("update failed, check the id and try again.")
    }

    exception<InValidJobTitleIdError> { call, _ ->
        call.badRequest("Invalid job title ID.")
    }

    exception<InValidContentError> { call, _ ->
        call.badRequest("Content is required and should not be empty")
    }

    exception<IOException> { call, _ ->
        call.badRequest("Invalid convert json file")
    }

    exception<JobTitlesAlreadyExist> { call, _ ->
        call.badRequest("Job Titles Already Exist")
    }


}