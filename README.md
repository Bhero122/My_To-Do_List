# Project 1 - *My_To-Do_List*

**My_To-Do_List** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Victoria**

Time spent: **50** hours spent in total

## User Stories

The following **required** functionality is completed:

* [X] User can **view a list of todo items**
* [X] User can **successfully add and remove items** from the todo list
* [X] User's **list of items persisted** upon modification and and retrieved properly on app restart

The following **optional** features are implemented:

* [X] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list

The following **additional** features are implemented:

* [ ] List anything else that you can get done to improve the app functionality!

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='ToDoList_FullWalkthrough.gif' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

* Had to change usb connection from file transfer to MIDI to download the app on a physical phone
* Had to change "implementation 'commons-io: commons-io: 2.8'" to "implementation group: 'commons-io', name: 'commons-io', version: '2.8.0'" in build.gradle so that the commons-io library would work properly
* Had to use the import "org.apache.commons.io.FileUtils" in MainActivity.java since "android.os.FileUtils" is the wrong import that Android Studio wants add
* Had to make sure to use Java instead of Kotlin when setting up the app in the beginning 

## License

    Copyright [yyyy] [name of copyright owner]

    Licensed under the Apache License, Version 2.8 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.X

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
