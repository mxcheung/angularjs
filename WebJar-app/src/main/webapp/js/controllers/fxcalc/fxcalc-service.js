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
            	{params: {
	            		sellAmount:sellAmount,
	            		fxrate:fxrate
            			}
            	})
            	.then(function(response) {
            		var lookupData = response.data;
            		return lookupData;
            	});
	}

	
	this.updateBuyAmount = function(depositData ) {
    	var requestData = JSON.parse(JSON.stringify(depositData));
    	requestData['buyAmount'] = depositData.buyAmount.toString().replace(/,/g, '');
    	requestData['tradeDate'] = this.formatDateForRequest(depositData.tradeDate);
    	requestData['expiryDate'] = this.formatDateForRequest(depositData.expiryDate);
    	 var json = JSON.stringify(depositData);
    	return $http.post("./fxcalc/update-buy-amount/"+depositData.id, requestData);  
    	
	}

	this.verify = function(depositData ) {
    	var requestData = JSON.parse(JSON.stringify(depositData));
    	requestData['buyAmount'] = depositData.buyAmount.toString().replace(/,/g, '');
    	requestData['tradeDate'] = this.formatDateForRequest(depositData.tradeDate);
    	requestData['expiryDate'] = this.formatDateForRequest(depositData.expiryDate);
    	 var json = JSON.stringify(depositData);
    	return $http.post("./fxcalc/verify/"+depositData.id, requestData);  
    	
	}
	
	this.formatDateForRequest = function(date) {
    	if(date) {
    		return moment(date).format('YYYY-MM-DD');
    	}
    }
	
});
