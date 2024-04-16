﻿using System.ComponentModel.DataAnnotations;

namespace AppChat.Models
{
    public class PostModel
    {
        public string? PostId
        { get; set; }
        [Required]
        public string? UserId
        { get; set; }
        [Required]

        public DateTime PostTime { get; set; }


        public List<ImagePostModel>? imagePost { get; set; }

        public string? Content
        { get; set; }
        [Required]
        public string? PostType { get; set; }

    }
}
