# Swipeable-RecyclerView
A library that provides an easy and customizable way to implement a swipe to dismiss pattern with RecyclerView.

**Note:** This library is currently in its infancy and thus shouldn't be used in production versions as of yet. If you are comfortable experimenting with this library though feel free to give it a spin and report any issues you find. A list of issues currently on the roadmap can be found [here](https://github.com/TR4Android/Swipeable-RecyclerView/issues).

## Usage
### Importing the library

This library will make it to maven eventually, but since it currently is in active development you'll have to build it yourself by cloning or downloading this repo.

### Code Setup

To be able to use the swipe to dismiss pattern in your RecyclerView you'll have to extend the `SwipeAdapter` in your Adapter class. After that there are only a few minor changes you have to do to get everything going:

##### Migrating from normal adapter
Override `onCreateSwipeViewHolder(ViewGroup parent, int viewType)` and `onBindSwipeViewHolder(ViewHolder holder, int position)` instead of the usual `onCreateViewHolder(ViewGroup parent, int viewType)` and `onBindViewHolder(ViewHolder holder, int position)`. This is needed to wrap your list item in a ViewGroup that handles swiping (namely `SwipeItem`) and handle its configuration. I additon to that you'll also have to replace the boolean `attachToRoot` with `true` so your list item gets attached to the wrapping SwipeItem.
A full implementation might look something like this:
``` java
public class SampleAdapter extends SwipeAdapter {
...
@Override
    public RecyclerView.ViewHolder onCreateSwipeViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sample, parent, true);
        SampleViewHolder sampleViewHolder = new SampleViewHolder(v);
        return sampleViewHolder;
    }
    
@Override
    public void onBindSwipeViewHolder(RecyclerView.ViewHolder holder, int position) {
        // handle data
    }
...
}
```

##### Special setup for swipeable adapter
There are some new methods related to the swiping pattern in the `SwipeAdapter` that you'll have to override. Those are:

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
