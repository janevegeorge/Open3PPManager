function autocompleteLibraryName() {

    $( "input[id='library.name']" ).autocomplete({
        serviceUrl: '/api/libraries',
        paramName: 'namePattern',
        deferRequestBy: 100,
        transformResult: function(libraries) {
            return {
                suggestions: $.map(JSON.parse(libraries), function(library) {
                    return { value: ((library.softwareData && library.softwareData.vendor) ? library.softwareData.vendor + " ": "")
                             + library.name,
                             data: library };
                })
            };
        },
        onSelect: function (suggestion) {
            if(suggestion != null && suggestion.data != null) {
                $( "input[id='library.name']" ).val(suggestion.data.name);
                $( "input[id='library.id']" ).val(suggestion.data.id);

                if(suggestion.data.softwareData != null) {
                    if(suggestion.data.softwareData.vendor!= null) $( "input[id='library.softwareData.vendor']" ).val(suggestion.data.softwareData.vendor);
                    if(suggestion.data.softwareData.website!= null) $( "input[id='library.softwareData.website']" ).val(suggestion.data.softwareData.website);

                    if(suggestion.data.softwareData.softwareType!= null) {
                        var softwareType = $('select[id="library.softwareData.softwareType"] option[value="' + suggestion.data.softwareData.softwareType + '"]');
                        if(softwareType != null) {
                            softwareType.prop('selected', true);
                        } else {
                            $( 'select[id="library.softwareData.softwareType"] option[value="UNKNOWN"]').prop('selected', true)
                        }
                    }

                    if(suggestion.data.softwareData.platform!= null) $( "input[id='library.softwareData.platform']" ).val(suggestion.data.softwareData.platform);
                }

                if(suggestion.data.tradeCompliance != null) {
                    if(suggestion.data.tradeCompliance.countryCode!= null) $( "input[id='library.tradeCompliance.countryCode']" ).val(suggestion.data.tradeCompliance.countryCode);
                    if(suggestion.data.tradeCompliance.euECCN!= null) $( "input[id='library.tradeCompliance.euECCN']" ).val(suggestion.data.tradeCompliance.euECCN);
                    if(suggestion.data.tradeCompliance.usECCN!= null) $( "input[id='library.tradeCompliance.usECCN']" ).val(suggestion.data.tradeCompliance.usECCN);
                    if(suggestion.data.tradeCompliance.bisAuthorization!= null) $( "input[id='library.tradeCompliance.bisAuthorization']" ).val(suggestion.data.tradeCompliance.bisAuthorization);
                    if(suggestion.data.tradeCompliance.encryptionProtocol!= null) $( "input[id='library.tradeCompliance.encryptionProtocol']" ).val(suggestion.data.tradeCompliance.encryptionProtocol);
                }
            }
        }
    });

    $( "input[id='library.name']" ).keyup(function(e){
        if(e.key.length == 1 || e.key == 'Backspace' || (e.key.length > 1 && /[^a-zA-Z0-9]/.test(e.key))) {
            $( "input[id='library.id']" ).val('');
            $( "input[id='library.softwareData.vendor']" ).val('');
            $( "input[id='library.softwareData.website']" ).val('');
            $( 'select[id="library.softwareData.softwareType"] option:eq(0)').prop('selected', true);
            $( "input[id='library.softwareData.platform']" ).val('');
            $( "input[id='library.tradeCompliance.countryCode']" ).val('');
            $( "input[id='library.tradeCompliance.euECCN']" ).val('');
            $( "input[id='library.tradeCompliance.usECCN']" ).val('');
            $( "input[id='library.tradeCompliance.bisAuthorization']" ).val('');
            $( "input[id='library.tradeCompliance.encryptionProtocol']" ).val('');
        }
    });

}



function autocompleteLibraryVersion() {

    $( "input[id='version']" ).autocomplete({
        serviceUrl: function() { return '/api/libraries/' + $( "input[id='library.id']" ).val() + '/versions'; },
        paramName: 'versionPattern',
        deferRequestBy: 100,
        transformResult: function(releases) {
            return {
                suggestions: $.map(JSON.parse(releases), function(release) {
                    return { value: release.version, data: release };
                })
            };
        },
        onSelect: function (suggestion) {
            if(suggestion != null && suggestion.data != null) {
                $( "input[id='id']" ).val(suggestion.data.id);
                $( "input[id='downloadUrl']" ).val(suggestion.data.downloadUrl);
            }
        }
    });

}











$( function() {
    autocompleteLibraryName();
    autocompleteLibraryVersion();
});