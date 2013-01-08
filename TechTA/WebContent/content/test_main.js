/*
 * jQuery File Upload Plugin JS Example 6.7
 * https://github.com/blueimp/jQuery-File-Upload
 *
 * Copyright 2010, Sebastian Tschan
 * https://blueimp.net
 *
 * Licensed under the MIT license:
 * http://www.opensource.org/licenses/MIT
 */

/*jslint nomen: true, unparam: true, regexp: true */
/*global $, window, document */

$(function () {
    'use strict';

    // Initialize the jQuery File Upload widget:
    $('#fileupload').fileupload();

    // Enable iframe cross-domain access via redirect option:
    $('#fileupload').fileupload(
        'option',
        'redirect',
        window.location.href.replace(
            /\/[^\/]*$/,
            '/cors/result.html?%s'
        )
    );

    if (true||window.location.hostname === 'blueimp.github.com') {
        // Demo settings:
        $('#fileupload').fileupload('option', {
            url: 'UploadFile',
            maxFileSize: 5000000,
            acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
            process: [
                {
                    action: 'load',
                    fileTypes: /^image\/(gif|jpeg|png)$/,
                    maxFileSize: 20000000 // 20MB
                },
                {
                    action: 'resize',
                    maxWidth: 1440,
                    maxHeight: 900
                },
                {
                    action: 'save'
                }
            ]
			,add:function (e, data) {
				alert("add file");
			}
			,submit:function (e, data) {
				data.formData = {clid: NowClass.clid};
				if (!data.formData.clid) {
				  return false;
				}
			}
        });
        // Upload server status check for browsers with CORS support:
        if ($.support.cors) {
            $.ajax({
                url: 'UploadFile',
                type: 'HEAD'
            }).fail(function () {
                $('<span class="alert alert-error"/>')
                    .text('Upload server currently unavailable - ' +
                            new Date())
                    .appendTo('#fileupload');
            });
        }
    } else {
        // Load existing files:
        $('#fileupload').each(function () {
            var that = this;
            $.getJSON(this.action, function (result) {
				
            });
			var result =  [
				  {
					"name": "picture1.jpg",
					"size": 902604,
					"url": "http:\/\/example.org\/files\/picture1.jpg",
					"thumbnail_url": "http:\/\/example.org\/files\/thumbnail\/picture1.jpg",
					"delete_url": "http:\/\/example.org\/files\/picture1.jpg",
					"delete_type": "DELETE"
				  },
				  {
					"name": "picture2.jpg",
					"size": 841946,
					"url": "http:\/\/example.org\/files\/picture2.jpg",
					"thumbnail_url": "http:\/\/example.org\/files\/thumbnail\/picture2.jpg",
					"delete_url": "http:\/\/example.org\/files\/picture2.jpg",
					"delete_type": "DELETE"
				  }
				]
			
			
			
			
		
			if (result && result.length) {
				var a = $(that).fileupload('option', 'done').call(that, null, {result: result});
			}
        });
    }

});
