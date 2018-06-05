app.service('ModalService', function($uibModal) {
    return {
        getModalInstance: function(templateUrl, modalInstanceController, resolve, suppliedOptions) {
            var modalProperties = {
                animation: false,
                templateUrl: templateUrl,
                controller: modalInstanceController,
                resolve: resolve,
                backdrop: false
            };

            //If any objects have been supplied, add them to the modal properties
            if (suppliedOptions !== undefined) {
                for (var attrname in suppliedOptions) {
                    modalProperties[attrname] = suppliedOptions[attrname];
                }
            }
            return $uibModal.open(modalProperties);
        }
    };
});

