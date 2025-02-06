/**
 * Form을 Object로 변환한다.
 */
function getFormObject2(formId) {

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
    
    console.log( JSON.stringify(result, null, 4 ) );
    
    return result;
}

function getFormObject(formId) {
    const form = document.getElementById(formId);
    const formData = new FormData(form);
    const result = {};

    formData.forEach((value, key) => {
        // Split the key by '.' or '['
        const keys = key.split(/[\.\[\]]/).filter(k => k);
        let obj = result;

        keys.forEach((k, i) => {
            if (i === keys.length - 1) {
                // 마지막 키는 값을 설정
                if (Array.isArray(obj[k])) {
                    obj[k].push(value);
                } else if (obj[k] !== undefined) {
                    obj[k] = [obj[k], value];
                } else {
                    obj[k] = value;
                }
            } else {
                // 중간 키는 객체 또는 배열을 설정
                if (!obj[k]) {
                    if (isNaN(keys[i + 1])) { // 다음 키가 숫자가 아니면 객체
                        obj[k] = {};
                    } else { // 다음 키가 숫자이면 배열
                        obj[k] = [];
                    }
                }
                obj = obj[k];
            }
        });
    });

    console.log( JSON.stringify(result, null, 4 ) );
    
    return result;
}

//브라우저 닫기 이벤트
function beforeunload(){
	$(window).bind("beforeunload", function (e){ 
		e.preventDefault();
		return false;
	});
}

/**
 * 최대값 보다 크게 입력 막기
 */
function checkAndLimitByteLength(inputEle) {
	
    const maxLength = parseInt(inputEle.attr('maxlength'), 10);
    const inputValue = inputEle.val();
    let byteSize = new Blob([inputValue]).size;

    if (byteSize > maxLength) {
        let truncatedValue = inputValue;
        while (byteSize > maxLength) {
            truncatedValue = truncatedValue.slice(0, -1);
            byteSize = new Blob([truncatedValue]).size;
        }
        inputEle.val(truncatedValue);
    }
}

/**
 * 바이트 크기 보여주기
 */
function viewByteLength(inputEle){
	const byteLengthDiv = inputEle.closest('tr').find('.byteLength');
	if( byteLengthDiv.length === 0 ){
		return false;
	}
	
    const inputValue = inputEle.val();
    const byteSize = getByteSize(inputValue);
    const maxLength = inputEle.attr('maxlength');
    
    byteLengthDiv.find('.current-size').text(byteSize);
    byteLengthDiv.find('.max-size').text(maxLength);
}

// Function to calculate byte size of a string
function getByteSize(str) {
    return new Blob([str]).size;
}
