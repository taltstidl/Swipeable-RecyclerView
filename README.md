# Swipeable-RecyclerView
A library that provides an easy and customizable way to implement a swipe to dismiss pattern with RecyclerView.

**Note:** This library is currently in its infancy and thus shouldn't be used in production versions as of yet. If you are comfortable experimenting with this library though feel free to give it a spin and report any issues you find. A list of issues currently on the roadmap can be found [here](https://github.com/TR4Android/Swipeable-RecyclerView/issues).

## Usage
### Importing the library

This library will make it to maven eventually, but since it currently is in active development you'll have to build it yourself by cloning or downloading this repo.

### Code Setup

To be able to use the swipe to dismiss pattern in your RecyclerView you'll have to extend the `SwipeAdapter` in your Adapter class. After that there are only a few minor changes you have to do to get everything going:

##### Migrating from normal adapter
Override `onCreateSwipeViewHolder(ViewGroup parent, int viewType)` and `onBindSwipeViewHolder(ViewHolder holder, int position)` instead of the usual `onCreateViewHolder(ViewGroup parent, int viewType)` and `onBindViewHolder(ViewHolder holder, int position)`. This is needed to wrap your list item in a ViewGroup that handles swiping (namely `SwipeItem`) and handle its configuration. In additon to that you'll also have to replace the boolean `attachToRoot` with `true` so your list item gets attached to the wrapping SwipeItem.

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

* `onCreateSwipeConfiguration(int position)`: This is used to determine the configuration of a particular list item and allows flexible control on a per item basis. You'll have to return a `SwipeConfiguration` using the built in `Builder` class. More customization options can be found in the SwipeConfiguration section below.
* `onSwipe(int position, int direction)`: This gets called whenever an item is removed using a swipe. Be sure to call `notifyItemRemoved(position)` there after changing your data to properly allow removal using the default ItemAnimator of the RecyclerView. `int direction` is one of either `SWIPE_LEFT` or `SWIPE_RIGHT` indicating the direction in which the user has dismissed the item.

An implementation might look like this:
``` java
public class SampleAdapter extends SwipeAdapter {
    ...
    @Override
    public SwipeConfiguration onCreateSwipeConfiguration(int position) {
        return new SwipeConfiguration.Builder()          
            .setBackgroundColor(mContext.getResources().getColor(R.color.color_delete))
            .setDrawableResId(R.drawable.ic_delete_white_24dp)
            .build();
    }
    
    @Override
    public void onSwipe(int position, int direction) {
        mDataset.remove(position);
        notifyItemRemoved(position);
    }
    ...
}
```

### Customization

You can easily customize the actions when swiping by using the `SwipeConfiguration` class which gives you full control over various aspects of this library. The following is a list of all currently available options. For all those there is also a corresponding `setLeft...()` and `setRight...()` flavor.

* `setBackgroundColor(int resolvedColor)`: The background color that appears behind the list item.
* `setDrawableResId(int resId)`: The resource id of the drawable shown as a hint for the action.
* `setDescriptionTextColor(int resolvedColor)`: The text color used for the description and undo text.
* `setDescription(CharSequence description)`: The text shown as a hint for the action.
* `setUndoDescription(CharSequence description)`: The text shown when the user has dismissed the item and is shown the option to undo the dismissal.
* `setUndoable(boolean undoable)`: Whether the action is undoable. If set to `true` the user will have the option to undo the action for 5 seconds, if set to `false` the item will be dismissed immediately.
* `setSwipeBehaviour(SwipeBehavior swipeBehavior)`: The behaviour of the item when swiping. Takes one of the provided default behaviours `NORMAL_SWIPE` or `RESTRICTED_SWIPE`.
* `setSwipeBehaviour(float swipeRange, Interpolator swipeInterpolator)`: The more customized behaviour of the item when swiping, where `swipeRange` indicates how far the item can be swiped (percentage of item width) and `swipeInterpolator` is the custom Interpolator used when calculating the item position while swiping.
* `setCallbackEnabled(callbackEnabled)`: Whether the swipe callback should be triggered on this action. If set to `true` you will receive a swipe action through `onSwipe(int position, int direction)`, if set to `false` you won't.

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
