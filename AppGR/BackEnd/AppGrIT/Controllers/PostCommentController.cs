﻿using AppGrIT.Helper;
using AppGrIT.Model;
using AppGrIT.Models;
using AppGrIT.Services;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using AppGrIT.Authentication;

namespace AppGrIT.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class PostCommentController : ControllerBase
    {

        private readonly IPostComments _postCommentManager;
        private readonly IUsers _userManager;
        
        private readonly IToken _tokenManager;

        public PostCommentController(IPostComments comment, IUsers userManager, IToken tokenManager)
        {
            _postCommentManager = comment;
            _tokenManager = tokenManager;
            _userManager = userManager;
        }
        [HttpGet("/get-post-comment")]
        public async Task<IActionResult> GetListCoverUser(string postId)
        {
            var result = await _postCommentManager.GetPostComment(postId);
            if (result.Count > 0)
            {
                return Ok(result);
            }
            return NotFound();
        }
        [Authorize(Roles = SynthesizeRoles.CUSTOMER)]
        [HttpPost("/add-comment-post")]
        public async Task<IActionResult> AddPostCommentUser([FromBody] PostCommentModel model)
        {
            var user = await _userManager.GetUserToUserId(model.UserId!);
            if (user != null)
            {
                var token = HttpContext.GetTokenAsync(JwtBearerDefaults.AuthenticationScheme, "access_token");
                string accesss_token = token.Result!;
                if (_tokenManager.CheckDupEmailToToken(accesss_token, user.Email))
                {
                    var result = await _postCommentManager.CreatePostCommentAsync(model);

                    if (result != null)
                    {

                        return Ok(result);
                    }
                    else
                    {
                        BadRequest(new ResponseModel
                        {
                            Status = StatusResponse.STATUS_ERROR,
                            Message = MessageResponse.MESSAGE_CREATE_FAIL
                        });
                    }
                }
                return Unauthorized();
            }
            return NotFound();
        }
        [Authorize(Roles = SynthesizeRoles.CUSTOMER)]
        [HttpDelete("/delete-comment-post")]
        public async Task<IActionResult> DeletePostCommentUser(string commentId, string userId, string postId)
        {
            var user = await _userManager.GetUserToUserId(userId);
            var cmt = await _postCommentManager.CheckCommentDupUser(postId,commentId,userId);
            if (user != null && cmt)
            {
                var token = HttpContext.GetTokenAsync(JwtBearerDefaults.AuthenticationScheme, "access_token");
                string accesss_token = token.Result!;
                if (_tokenManager.CheckDupEmailToToken(accesss_token, user.Email))
                {
                    
                    var result = await _postCommentManager.DeleteCommentAsync(commentId);

                    if (result.Status!.Equals(StatusResponse.STATUS_SUCCESS))
                    {

                        return Ok(result);
                    }
                    else
                    {
                        BadRequest(result);
                    }
                }
                return Unauthorized();
            }
            return NotFound();
        }
    }
}

