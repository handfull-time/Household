/**
 * Toast 설정
 * @see https://codeseven.github.io/toastr/demo.html
 */
toastr.options = {
	
	'closeButton': true
	, 'debug': false
	, 'newestOnTop': true
	, 'progressBar': true
	, 'positionClass': 'toast-top-center' //'toast-top-full-width' 
	, 'preventDuplicates': false
	, 'onclick': null
	, 'showDuration': '3000'
	, 'hideDuration': '500'
	, 'timeOut': '3500'
	, 'extendedTimeOut': '0'
	, 'showEasing': 'swing'
	, 'hideEasing': 'linear'
	, 'showMethod': 'fadeIn'
	, 'hideMethod': 'fadeOut'
};

/**
 * Toast Confirm
 * 크... 이 기능을 쓰면 이쁘긴 한데 ... 소스 수정할 것이 너무 많아지네... 음.... 
 * @param title : 제목 
 * @param message : 내용
 * @param yesAction : 예 클릭 이벤트
 * @param noAction : 아니오 클릭 이벤트
 */
function toastConfirm(title, message, yesAction, noAction){
	let element="<div align='right' style='margin-top:10px;'>";
	element += "<button type='button' id='confirmYes' class='btn btn-primary'>예</button>";
	element += "&nbsp;&nbsp;";
	element += "<button type='button' id='confirmNo' class='btn btn-danger'>아니오</button>";
	element += "</div>";

	const toast = toastr.info(message + element, title,
		{
			closeButton: false,
			allowHtml: true,
			maxOpened: 1,
			timeOut: 5000,
			progressBar: true,
			onShown: function () {
				$("#confirmYes").click(function(){
					console.log('Yes click');
					
					toastr.clear(toast);
					if( typeof yesAction === 'function' )
						yesAction();
			    });
				$("#confirmNo").click(function(){
					console.log('No click');

					toastr.clear(toast);
					if( typeof noAction === 'function' )
						noAction();
			    });
			}
		});
}

/**
 * Input Value 가 필요할 때
 * @param title : 제목 
 * @param message : 내용
 * @param defaultVal : 기본 값
 * @param okAction : 버튼 클릭 이벤트
 */
function toastInput(title, message, defaultVal, okAction){
	
	let element="<div>";
	element += "<input type='text' id='inputToastTxt' class='form-control' style='width:95%;margin-top:10px;' value='"+defaultVal+"'><br/>";
	element += "<button type='button' id='confirmationRevertOk' class='btn btn-primary' style='float: right;'>확인</button>";
	element += "</div>";
	
	const toast = toastr.info(message + element, title, {
		closeButton: true,
		allowHtml: true,
		timeOut: 0,
		maxOpened: 1,
		progressBar: false,
		hideDuration: 100,
		onShown: function () {
			$('#inputToastTxt').focus();
			
			$("#confirmationRevertOk").click(function(){
				console.log('Ok click');
			
				const txtVal = $("#inputToastTxt").val().trim();
				
				toastr.clear(toast);
				if( typeof okAction === 'function' && txtVal.length > 0 ){
					okAction( txtVal );
				}

		    });
		},
		onclick:function( owner ){
			// 이 이벤트가 있어야 자동 클리어가 되지 않더라...
		}
	});
}

const toastrAlertOptions = {
    closeButton: true,
    allowHtml: false,
    timeOut: 2000,
    maxOpened: 1,
    progressBar: false,
    hideDuration: 400,
    hideMethod: 'slideUp',
    showDuration: 400,
    showMethod: 'slideDown'
};

function toastAlert( title, message , type = "info") {
	
    switch (type) {
        case "info": toastr.info(message, title, toastrAlertOptions); break;
        case "error": toastr.error(message, title, toastrAlertOptions); break;
        case "warning": toastr.warning(message, title, toastrAlertOptions); break;
        case "success": toastr.success(message, title, toastrAlertOptions); break;
        default: toastr.info(message, title, toastrAlertOptions); break;
    }
}

function _ajaxError(request, status, error) {
	console.log('code: ', request.status);
    console.log('message: ', request.responseText);
    console.log('status: ', status);
    console.log('error: ', error);
	
	toastAlert('오류', '오류가 발생했습니다.' + request.status + ', ' + request.responseText, 'error');	
}

function doSearchList(urlAddress, sendData, resultId){
	$.ajax({
		type : 'POST',
		url : urlAddress,
		data : sendData,
		dataType : 'html',
		beforeSend: function(){
			showWaitingPopupLayer();
		},
		success:function(data){ 
			$('#' + resultId).empty().html(data).show();
		},
		error: _ajaxError,
		complete: function(){
			closeWaitingPopupLayer();
		}
	});
}

//대기 중 모달 표시
function showWaitingPopupLayer() {
    $('#waitingModal').modal({
        backdrop: 'static',
        keyboard: false
    });
}

// 대기 중 모달 숨김
function closeWaitingPopupLayer() {
    $('#waitingModal').modal('hide');
}

function onShowModalLayer(urlAddress, sendData, modalId){
	
	$.ajax({
		type : 'POST',
		url : urlAddress,
		data : sendData,
		dataType : 'html',
		success:function(data){ 
			const DetailLayer = $('#DetailLayer');
			
			DetailLayer.empty();
			DetailLayer.html(data).show();
			const modal = $('#' + modalId);
			modal.on('hidden.bs.modal', function () {
				console.info('모달 내부의 HTML 비우기');
				$('#DetailLayer').empty();
			});
			modal.modal('show');
		},
		error: _ajaxError,
	});
}

function onHideModalLayer( modalId ){
	$('#' + modalId).remove();
	$('.modal-backdrop').remove();
	$('#DetailLayer').empty().html('').hide();
}

/**
 * ajax 전송
 */
function onJsonAction(urlAddress, sendData, resultAction){
	$.ajax({
        type: 'POST',
        url: urlAddress,
        data: sendData,
        dataType: 'json',
        success: function(json) {
        	resultAction( json );
        },
        error: _ajaxError
    });
}

function onToJsonAction(urlAddress, sendData, resultAction){
	$.ajax({
        type: 'POST',
        url: urlAddress,
        data: JSON.stringify(sendData),
        contentType: 'application/json',
        dataType: 'json',
        success: function(json) {
        	resultAction( json );
        },
        error: _ajaxError
    });
}

/**
 * Form을 Object로 변환한다.
 */
function getFormObject(formId) {

    const form = document.getElementById(formId);
    
    const formData = new FormData(form);
    
    const result = {};
    formData.forEach((value, key) => {
		
		const keys = key.split('.'); // Split key into array of keys
        let obj = result;

        for (let i = 0; i < keys.length; i++) {
            const k = keys[i];

            if (i === keys.length - 1) {
                // If it's the last key, set the value
                if (Array.isArray(obj[k])) {
                    obj[k].push(value);
                } else if (obj[k] !== undefined) {
                    obj[k] = [obj[k], value];
                } else {
                    obj[k] = value;
                }
            } else {
                // If it's not the last key, ensure the nested object exists
                if (!obj[k]) {
                    obj[k] = {};
                }
                obj = obj[k];
            }
        }
    });
    
    console.log(result);
    
    return result;
}

function viewByteLength(inputEle){
    const inputValue = inputEle.val();
    const byteSize = getByteSize(inputValue);
    const maxLength = inputEle.attr('maxlength');
    
    const byteLengthDiv = inputEle.closest('tr').find('.byteLength');
    byteLengthDiv.find('.current-size').text(byteSize);
    byteLengthDiv.find('.max-size').text(maxLength);
}

// Function to calculate byte size of a string
function getByteSize(str) {
    return new Blob([str]).size;
}
