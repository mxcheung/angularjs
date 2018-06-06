app.service('FxCalcService', function($http) {
	this.getcalcSellAmount = function(buyAmount,fxrate ) {
       	return $http.get("./fxcalc/calc-sell-amount", 
            	{params: {buyAmount:buyAmount,fxrate:fxrate}})
            	.then(function(response) {
            		var lookupData = response.data;
            		return lookupData;
            	});
	}

	this.getcalcBuyAmount = function(sellAmount,fxrate ) {
       	return $http.get("./fxcalc/calc-buy-amount", 
            	{params: {sellAmount:sellAmount,fxrate:fxrate}})
            	.then(function(response) {
            		var lookupData = response.data;
            		return lookupData;
            	});
	}
	
});
