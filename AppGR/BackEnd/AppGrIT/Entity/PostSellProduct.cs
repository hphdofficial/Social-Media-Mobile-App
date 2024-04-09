﻿using System.ComponentModel.DataAnnotations;

namespace AppGrIT.Entity
{
    public class PostSellProduct
    {
        [Required]
        [Key]
        public string PostSellProductId
        { get; set; } = null!;

        [Required]
        public string UserId
        { get; set; } = null!;

    
        public string Content
        { get; set; } = null!;

        [Required]
        public float Price
        { get; set; }
        [Required]
        public string NameProduct
        { get; set; }
        public DateTime PostTime { get; set; }
    }
}
