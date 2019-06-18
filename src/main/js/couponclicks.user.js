// ==UserScript==
// @name        couponclicks
// @namespace   digitalcouponclicker
// @description coupon clicks for publix, this is old, had stopped working, and is no longer in a servicable state
// @include     https://www.publix.com/savings/coupons/digital-coupons
// @version     1
// @grant       none
// ==/UserScript==
console.log("couponclicks was invoked");
function clickerFunc() {
  var buttons = document.querySelectorAll('.dc-card-clip');
  console.log("buttons length: " + buttons.length);
  for(j = 0; j < 6; j++) {
    var segmentLength = 5; // 30 buttons, 6 breakouts = 5 per segment
    var startIndex = j * segmentLength;
    for(i = startIndex; i < startIndex + segmentLength; i++) {
      setTimeout(function(button, i){return clickingFunc(button, i)}(buttons[i], i), j * 2000);
      //clickingFunc(buttons[i], i);
    }
  }
};

function clickingFunc(button, i) {
  button.click();
  button.setAttribute('style', '');
  button.setAttribute('class', '');
}

window.onload = function() {
  console.log("hi there!");
  setTimeout(clickerFunc, 12000);
};
