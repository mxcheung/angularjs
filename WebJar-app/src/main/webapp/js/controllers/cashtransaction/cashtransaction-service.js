app.service('CashTransactionService', function($http) {
	return {
		createCashTransaction : function(CashTransaction) {
			// Convert the object to and from JSON to clone it (we don't want to edit the field values inline)
            var requestData = JSON.parse(JSON.stringify(CashTransaction));
            return $http.post("cash-transaction/create-cash-transaction", requestData);
		},
		getCashTransactionsByCriteria : function(displayDeleted) {
			return $http.get('./cash-transaction/list-by-criteria', {
                params: {
                    'display-deleted': displayDeleted
                }
        });
		},

	    getCashTransaction : function (cashTransactionId) {
	    	return $http.get("cash-transaction/"+cashTransactionId);
	    },
	    		
	    deleteCashTransaction : function (cashTransactionId) {
	    	return $http.delete("cash-transaction/"+cashTransactionId)
	    },
	    
	    updateCashTransaction : function (cashTransactionData) {
	    	var requestData = JSON.parse(JSON.stringify(cashTransactionData));
	    	return $http.post("cash-transaction/"+cashTransactionData.id, requestData);
	    }	    
	};

		
});


