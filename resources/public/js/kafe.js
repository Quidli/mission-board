$(window).load(function () {
	
		/* VEGAS Home Slider */
	
	$("#header").vegas({
		slides: [
			{ src: "img/slider/1.jpg" },
			{ src: "img/slider/2.jpg" },
			{ src: "img/slider/3.jpg" },
			{ src: "img/slider/4.jpg" },
			{ src: "img/slider/5.jpg" },
			{ src: "img/slider/6.jpg" },
			{ src: "img/slider/7.jpg" },
			{ src: "img/slider/8.jpg" }
		],
        transition: 'fade',
        preloadImage: true,
        timer: true,
        shuffle: true,
        delay: 5000,
        animation: 'kenburns',
        cover: true
	});
	
	$("#video").vegas({
		slides: [
			{   src: 'videos/Productive-Morning.jpg' },
			{
			  video: {
                src: [
                    'videos/Productive-Morning.mp4',
                    'videos/Productive-Morning.webm',
                    'videos/Productive-Morning.ogv'
                ],
                loop: true,
                mute: true
               },
        delay: 16000,
		 }
		],
        cover: true
	});		
	
});

$(document).ready(function() {
						 
	// navigation click actions	
	// jQuery for page scrolling feature - requires jQuery Easing plugin

    $('.scroll-link').bind('click', function(event) {
        var $anchor = $(this);
	    var offSet = 85;
        $('html, body').stop().animate({
            scrollTop: $($anchor.attr('href')).offset().top - offSet
        }, 1500, 'easeInOutExpo');
        event.preventDefault();
    });
	
						   
	/*============================================
	Navigation Functions
	==============================================*/
	if ($(window).scrollTop()===0){
		$('.navbar').removeClass('scrolled');
	}
	else{
		$('.navbar').addClass('scrolled');    
	}

	$(window).scroll(function(){
		if ($(window).scrollTop()===0){
			$('.navbar').removeClass('scrolled');
		}
		else{
			$('.navbar').addClass('scrolled');    
		}
	});
	
	/*============================================
	Scroll To Top
	==============================================*/	

	//When distance from top = 250px fade button in/out
	$(window).scroll(function(){
		if ($(this).scrollTop() > 250) {
			$('#scrollup').fadeIn(300);
		} else {
			$('#scrollup').fadeOut(300);
		}
	});

	//On click scroll to top of page t = 1000ms
	$('#scrollup').click(function(){
		$("html, body").animate({ scrollTop: 0 }, 1000);
		return false;
	});
	
	/*============================================
	Scrolling Animations
	==============================================*/
	$(function() {
	
	  var $window           = $(window),
		  win_height_padded = $window.height() * 1.1,
		  isTouch           = Modernizr.touch;
	
	  if (isTouch) { $('.revealOnScroll').addClass('animated'); }
	
	  $window.on('scroll', revealOnScroll);
	
	  function revealOnScroll() {
		var scrolled = $window.scrollTop(),
			win_height_padded = $window.height() * 1.1;
	
		// Showed...
		$(".revealOnScroll:not(.animated)").each(function () {
		  var $this     = $(this),
			  offsetTop = $this.offset().top;
	
		  if (scrolled + win_height_padded > offsetTop) {
			if ($this.data('timeout')) {
			  window.setTimeout(function(){
				$this.addClass('animated ' + $this.data('animation'));
			  }, parseInt($this.data('timeout'),10));
			} else {
			  $this.addClass('animated ' + $this.data('animation'));
			}
		  }
		});
		// Hidden...
	   $(".revealOnScroll.animated").each(function (index) {
		  var $this     = $(this),
			  offsetTop = $this.offset().top;
		  if (scrolled + win_height_padded < offsetTop) {
			$(this).removeClass('animated fadeInUp flipInX lightSpeedIn')
		  }
		});
	  }
	
	  revealOnScroll();
	});

	/*============================================
	Skills
	==============================================*/

	$('.skills').waypoint(function(){
		$('.chart').each(function(){
		$(this).easyPieChart({
				size:140,
				animate: 2000,
				lineCap:'butt',
				scaleColor: false,
				barColor: '#37BC9B',
				trackColor: 'transparent',
				lineWidth: 5
			});
		});
	},{offset:'80%'});		
	
	/*============================================
	Appear JS
	==============================================*/	
    if ($.fn.appear) {		
        $('.number-animator').appear();
        $('.number-animator').on('appear', function () {
            $(this).animateNumbers($(this).attr("data-value"), true, parseInt($(this).attr("data-animation-duration")));
        });

        $('.animated-progress-bar').appear();
        $('.animated-progress-bar').on('appear', function () {
            $(this).css('width','0%').animate({ 'width': $(this).attr("data-percentage") }, 1000);
        });
    }

	/*============================================
	Animate Numbers
	==============================================*/
    if ($.fn.animateNumbers) {
        $('.animate-number').each(function () {
            $(this).animateNumbers($(this).attr("data-value"), true, parseInt($(this).attr("data-animation-duration")));
        })
    }
		

});

