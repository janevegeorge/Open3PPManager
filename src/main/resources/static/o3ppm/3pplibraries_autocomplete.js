/*!
 * Open3PPManager (https://github.com/janevegeorge/Open3PPManager)
 * Copyright 2020 Janeve.Me (http://www.janeve.me)
 *
 * This file is part of "Open3PPManager".
 *
 * Open3PPManager is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Open3PPManager is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Open3PPManager. If not, see <http://www.gnu.org/licenses/>.
 */
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
        serviceUrl: function() {
            if( $( "input[id='library.id']" ).val() == null || $( "input[id='library.id']" ).val() == '') {
                return '/api/libraries/NO_ID/versions';
            } else {
                return '/api/libraries/' + $( "input[id='library.id']" ).val() + '/versions';
            }
        },
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

    $( "input[id='version']" ).keyup(function(e){
        if(isValidEvent(e)) {
            $( "input[id='id']" ).val('');
        }
    });

}

function isValidEvent(e) {
    return ( e.key.length == 1 || e.key == 'Backspace' || (e.key.length > 1 && /[^a-zA-Z0-9]/.test(e.key)) );
}




$( function() {
    autocompleteLibraryName();
    autocompleteLibraryVersion();
});