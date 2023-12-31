package com.the_chance.endpoints.utils

import com.the_chance.data.utils.ServerResponse
import com.the_chance.utils.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.response.*
import io.ktor.util.pipeline.*

suspend fun PipelineContext<Unit, ApplicationCall>.tryQuery(query: suspend () -> Unit) {
    try {
        query()
    } catch (throwable: InValidContentError) {
        call.respond(HttpStatusCode.BadRequest, ServerResponse.error("Content should not be empty"))
    } catch (throwable: NoPostFoundError) {
        call.respond(HttpStatusCode.NoContent, ServerResponse.error("no post found"))
    } catch (throwable: InValidIdError) {
        call.respond(HttpStatusCode.BadRequest, ServerResponse.error("invalid id"))
    }catch (throwable: NoJobTitleFoundError) {
        call.respond(HttpStatusCode.NoContent, ServerResponse.error("Job title ID does not exist"))
    } catch (throwable: InValidJobError) {
        call.respond(HttpStatusCode.BadRequest, ServerResponse.error("All fields are required.."))
    } catch (throwable: InValidSalaryError) {
        call.respond(HttpStatusCode.BadRequest, ServerResponse.error("Invalid salary."))
    } catch (throwable: NoJobFoundError) {
        call.respond(HttpStatusCode.NotFound, ServerResponse.error("Opps!, this job not found."))
    } catch (throwable: DeleteError) {
        call.respond(HttpStatusCode.BadRequest, ServerResponse.success("delete failed, check the id and try again"))
    } catch (ex: ContentTransformationException) {
        call.respond(HttpStatusCode.BadRequest, ServerResponse.error(ex.message.toString()))
    } catch (e: Exception) {
        call.respond(HttpStatusCode.InternalServerError, ServerResponse.error(e.message.toString()))
    }
}