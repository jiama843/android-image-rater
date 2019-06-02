# A4

----------
# Overview

An android application called “Fotag - jl2ma” that downloads a set of images from the internet. You can load, clear, rate and filter them.


# List of Features

Requirements: https://www.student.cs.uwaterloo.ca/~cs349/w19/assignments/a4.html


## Toolbar
- A **load button**, which loads a set of 10 images over the network from this URL: [https://www.student.cs.uwaterloo.ca/~cs349/w19/assignments/images](https://www.student.cs.uwaterloo.ca/~cs349/w19/assignments/images). If the list already contains images, clicking on this button should clear the list and replace with a fresh set of images from that URL.
- A **clear button**, removes all images from the list.
- A **filter widget**, showing 5-stars, used to filter the image list based on rating. Supports filtering by "any" image, or "1-5" where the filter shows all images that have that rating or higher. **Drag** all the way to the left to clear filter


## UI
- Each image in the grid is shown as a thumbnail, with a set of 5-stars beneath it. Users can touch a star to select a rating, and they can clear the rating on an individual image by dragging the bar all the way to the left.
- Touching the image thumbnail in the grid will enlarge the image to full-screen (white background). Touching it a second time dismisses the window.
- Loading or clearing the list will clear the ratings. Ratings are not saved between sessions.
- Supports vertical layout with 1 image on each row, and horizontal layout, with 2 images on each row.

