# Coroutines Internals: Thread vs Coroutine Performance

This Android project demonstrates the performance differences between launching a large number of tasks using:

- A fixed thread pool with 40 threads
- A fixed thread pool with 4 threads (batched tasks)
- 100,000 coroutines with delay
- 100,000 raw threads with sleep

It allows side-by-side comparison of task execution time and system limits.

## Features

- Measure execution time for launching 10,000 tasks using 40 threads
- Measure execution time for batched 10,000 tasks using only 4 threads
- Launch 100,000 coroutines using `delay(5000L)`
- Launch 100,000 raw threads using `Thread.sleep(5000L)`
- Display execution results directly on the UI

## Experiments

### 1. Launch 10,000 Tasks Using 40 Threads

- Uses `Executors.newFixedThreadPool(40)`
- Launches 10,000 tasks concurrently
- Measures time taken to complete all tasks

### 2. Launch 10,000 Batched Tasks Using 4 Threads

- Uses `Executors.newFixedThreadPool(4)`
- Each thread runs 2,500 tasks in a loop
- Measures time taken for all 10,000 tasks to finish

### 3. Launch 100,000 Coroutines

- Uses `GlobalScope.launch` with `delay(5000L)`
- Measures when all coroutines complete
- Lightweight and non-blocking

### 4. Launch 100,000 Threads

- Launches raw threads with `Thread.sleep(5000L)`
- Shows how many threads can be created before failure
- Displays failure or completion status on UI

## UI Layout

- Buttons to trigger each experiment
- Result text views to display status or time taken
- Uses a `GridLayout` for clear side-by-side comparison

## How to Run

1. Clone the repository
2. Open the project in Android Studio
3. Build and run on a physical device or emulator (emulator may not handle 100,000 threads)
4. Tap each button to run the respective experiment
5. Observe results printed on screen

## Notes

- Launching 100,000 threads may cause an `OutOfMemoryError`
- Coroutine-based experiment is the most memory-efficient
- This project is intended for educational and benchmarking purposes

## Demo Video
https://github.com/user-attachments/assets/220c5b20-74f9-4968-8852-7f10c9ac626c


