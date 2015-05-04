# Swipeable-RecyclerView
A library that provides an easy and customizable way to implement a swipe to dismiss pattern with RecyclerView.

**Note:** This library is currently in its infancy and thus shouldn't be used in production versions as of yet. If you are comfortable experimenting with this library though feel free to give it a spin and report any issues you find.

## Usage
### Importing the library

This library will make it to maven eventually, but since it currently isn't ready you'll have to build it yourself by cloning or downloading this repo.

### Code Setup

To be able to use the swipe to dismiss pattern in your RecyclerView you'll have to extend the `SwipeAdapter` in your Adapter class. After that there are only a few minor changes you have to do to get everything going:

* Use `onCreateSwipeViewHolder(ViewGroup parent, int viewType)` instead of the usual `onCreateViewHolder(ViewGroup parent, int viewType)`. This is needed to wrap your list item in a ViewGroup that handles swiping (namely `SwipeItem`). I additon to that you'll also have to replace the boolean `attachToRoot` with `true` so your list item gets attached to the wrapping ViewGroup.
A full implementation might look something like this:
``` java
@Override
    public RecyclerView.ViewHolder onCreateSwipeViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sample, parent, true);
        SampleViewHolder sampleViewHolder = new SampleViewHolder(v);
        return sampleViewHolder;
    }
```
* Make sure you call `super.onBindViewHolder(holder, position)` in your `onBindViewHolder(ViewHolder holder, int position)` implementation. This is needed so the `SwipeAdapter` can handle the state of the swipe items.

There also are some new methods related to the swiping pattern in the `SwipeAdapter` that you'll have to override. Those are:

* `onCreateSwipeConfiguration(int position)`: This is used to determine the configuration of a particular list item and allows flexible control on a per item basis. You'll have to return a `SwipeConfiguration`. An implementation might look like this (More customisation options can be found below):
``` java
@Override
    public SwipeConfiguration onCreateSwipeConfiguration(int position) {
        SwipeConfiguration configuration = new SwipeConfiguration();
        configuration.setBackgroundColor(mContext.getResources().getColor(R.color.color_delete));
        configuration.setDescription(mContext.getResources().getString(R.string.action_delete));
        configuration.setDescriptionTextColor(mContext.getResources().getColor(android.R.color.white));
        configuration.setDrawableResId(R.drawable.ic_delete_white_24dp);
        return configuration;
    }
```
* `onSwipe(int position)`: This gets called whenever an item is removed using a swipe. You have to call `notifyItemRemoved(position)` there after changing you data.

## License

Copyright 2015 Thomas Robert Altstidl

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
