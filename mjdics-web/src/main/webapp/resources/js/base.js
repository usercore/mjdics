function showLoading(){
document.getElementById('popDivLoading').style.display='block';
document.getElementById('popIframe').style.display='block';
document.getElementById('bg').style.display='block';
}

function showContent(){
	document.getElementById('popDivLoading').style.display='none';
	document.getElementById('popDivContent').style.display='block';
}

function closeDiv(){
document.getElementById('popDivLoading').style.display='none';
document.getElementById('popDivContent').style.display='none';
document.getElementById('bg').style.display='none';
document.getElementById('popIframe').style.display='none';

}