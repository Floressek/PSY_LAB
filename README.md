# Laboratory - Random Number Generation and Probability Distributions

## Overview
This laboratory focuses on implementing and testing various random number generation methods and probability distributions using Java. The implementation includes a custom random number generator and methods for generating various probability distributions.

## Tasks Overview

### Task 1: Linear Congruential Generator
Implementation of a basic pseudorandom number generator using the Linear Congruential Method (LCM).

#### Key Components:
- Parameters: $a=97, b=11, M=100, seed=1$
- Formula: $x_{n+1} = (a * x_n + b) mod M$
- Generator outputs values in ranges:
    - $[0, M)$ for integers
    - $[0, 1.0)$ for doubles
    - Custom ranges using linear transformation
    - Exponential distribution using inverse transform method

#### Pseudo-cycle Analysis
The implemented generator has a cycle length of 20 values:
```
8, 87, 50, 61, 28, 27, 30, 21, 48, 67, 10, 81, 68, 7, 90, 41, 88, 47, 70, 1, [repeat]
```
When normalized to [0,1) range:
```
0.08, 0.87, 0.50, 0.61, 0.28, 0.27, 0.30, 0.21, 0.48, 0.67, 
0.10, 0.81, 0.68, 0.07, 0.90, 0.41, 0.88, 0.47, 0.70, 0.01, [repeat]
```
Note: This relatively short cycle length indicates limitations of the chosen parameters for serious statistical applications.

### Task 2: Custom Probability Distribution
Implementation of a specific probability distribution function with the following characteristics:


$
f(x) = {
    2x + 1  for x ∈ [-1, 0]
    4(x-0.5) for x ∈ [0, 2]
}
$

#### Key Features:
- Uses inverse transform method
- Expected value: 1/4
- Implemented in `distribution_1()` method
- Tested with 1000 iterations to verify mean

### Task 3: Discrete Distribution
Implementation of a discrete probability distribution generator.

#### Implementation Details: 
- Takes arrays of values (xx) and probabilities (p)
- Values: $[-2.0, -1.0, 0.0, 1.0, 2.0, 3.0, 4.0]$
- Probabilities: $[0.1, 0.2, 0.1, 0.05, 0.1, 0.3, 0.2]$
- Uses inverse transform method with cumulative probabilities
- Theoretical mean calculation included for verification

## Technical Implementation Notes

### RandomGenerator Class
Main class implementing all random generation methods:
- `nextInt()`: Basic integer generation
- `nextDouble()`: Generation of $[0,1)$ values
- `nextDouble(low, high)`: Range transformation
- `exponential(lambda)`: Exponential distribution
- `distribution_1()`: Custom distribution from Task 2
- `discrete(xx, p)`: Discrete distribution

### Testing Methods
Each task has its dedicated test method in Main class:
- `testTask1()`: Tests basic generator functionality
- `testTask2()`: Tests custom distribution
- `testTask3()`: Tests discrete distribution

### Statistical Verification
- Empirical means calculated from generated samples
- Theoretical means calculated for comparison
- Absolute differences reported for accuracy assessment

## Limitations and Considerations
1. The short cycle length $(20)$ of the basic generator limits its statistical applications
2. Parameters $(a, b, M)$ could be optimized for longer cycles
3. For serious applications, Java's built-in Random class would be more appropriate

## Running the Tests
Execute the Main class to run all tests sequentially. Each test produces detailed output including:
- Generated values
- Empirical means
- Theoretical means
- Absolute differences between empirical and theoretical values