/*!
 * Open3PPManager v0.1-SNAPSHOT (https://github.com/janevegeorge/Open3PPManager)
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
skipNext = false;
function autocompleteProjectName() {

    $( "input[id='name']" ).autocomplete({
        serviceUrl: '/api/projects',
        paramName: 'namePattern',
        deferRequestBy: 100,
        transformResult: function(projects) {
            return {
                suggestions: $.map(JSON.parse(projects), function(project) {
                    return { value: project.name, data: project };
                })
            };
        },
        onSelect: function (suggestion) {
            if(suggestion != null && suggestion.data != null) {
                console.log(suggestion.data);
                $( "input[id='id']" ).val(suggestion.data.id);
                $( "textarea[id='description']" ).val(suggestion.data.description);
            }
        }
    });

    $( "input[id='name']" ).keyup(function(e){
        if(e.key == "CTRL" || e.key == "ALT") { skipNext = true; return; }
        if(!skipNext) {
            if(e.key.length == 1 || e.key == 'Backspace' || (e.key.length > 1 && /[^a-zA-Z0-9]/.test(e.key))) {
               $( "input[id='id']" ).val('');
               $( "textarea[id='description']" ).val('');
            }
        }
        skipNext = false;
    });

}

$( function() {
    autocompleteProjectName();
});