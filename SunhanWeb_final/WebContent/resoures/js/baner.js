(function($) {
		var pathes = $('#XignifyLogo').find('path');
		pathes.each(function(i, path) {
			var total_length = path.getTotalLength(); // 1번 부분

			path.style.strokeDasharray = total_length + " " + total_length; // 2번 부분
			path.style.strokeDashoffset = total_length; // 3번 부분

			// 4번 부분
			$(path).animate({
				"strokeDashoffset" : 0
			}, 1600);
		});
		//}
	})(jQuery);