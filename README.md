# Pre-work - *SimpleTodo*

**SimpleTodo** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Karl Clark**

Time spent: **10** hours spent in total

## User Stories

The following **required** functionality is completed:

* [X] User can **successfully add and remove items** from the todo list
* [X] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [X] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [X] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [X] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [X] Add support for completion due dates for todo items. Completed items are listed in blue.  Past due items are listed in red. 
* [X] A dialog fragment is used in the edit activity to display the date picker.
* [X] Add support for selecting the priority of each todo item (and display in listview item)
* [X] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

* [X] Date field is pre-filled on edit screen.
* [X] On return from Add/Edit screen listview scrolls to appropriate position.

## Video Walkthrough 

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/Pl6hamN.gifv' title='Video Walkthrough' width='' alt='Video Walkthrough' /> 

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Challenges included:
Verticle line in listview item layout wouldn't show up in actual listview.  Never did work on GenyMotion emulator.
Trouble retrieving and setting background color on listview items.
Width of one of the textviews had to be set in code, so it would be the same size in all listview items, the length of the longest word used in the view.

## License

    Copyright [yyyy] [name of copyright owner]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
