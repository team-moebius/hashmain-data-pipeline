$(document).ready(function(){
	//	 input placeholder 
	var forms;
	forms = $('.l-input-text');
	forms.placeholder();
	
	// popup �ݱ�
	$('.l-popup_close').on('click', function(){
		$('.l-popup').removeClass('l-popup--active');
	});

	// tab
	$('.l-tab_link').on('click', function(){
		var idx = $(this).parent().index();

		if ( $(this).parent().index() == 1 ){
			$('.l-tabs').addClass('l-tabs--2');
		}else {
			$('.l-tabs').removeClass('l-tabs--2');
		}
			
		$('.l-tab_item').removeClass('l-tab_item--active');
		$(this).parent().addClass('l-tab_item--active');
		
		$('.l-target').removeClass('l-target--active');
		$('.l-target').eq(idx).addClass('l-target--active');

		return false;
	});

	// phone
	$('.l-phone .l-input-text').on('focus', function(){
		$('.l-phone').addClass('l-phone--focus');
		$('.l-phone .l-input-text').attr('placeholder','');
	});
	$('.l-phone .l-input-text').on('focusout', function(){
		if ( $('.l-phone .l-input-text').eq(1).val().length == 0 && $('.l-phone .l-input-text').eq(2).val().length == 0){
			$('.l-phone').removeClass('l-phone--focus');
			$('.l-phone .l-input-text').eq(0).attr('placeholder','Phone number');
		}
	});

});