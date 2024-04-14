﻿using System.ComponentModel.DataAnnotations;

namespace AppChat.Models
{
    public class GroupChatModel
    {
        
        public string GroupId { get; set; }
        [Required]
        public DateTime TimeCreate
        { get; set; }
        [Required]
        public string GroupName
        { get; set; }
        [Required]
        public int QuantityMember
        { get; set; }
    }
}
