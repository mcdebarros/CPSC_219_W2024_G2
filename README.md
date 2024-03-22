# The Matrix Object

This java program is an expansion of the previously developed Regression class. It has been expanded to incorporate a unique Matrix object and useful methods thereof. The regression class has been restructured to operate on the matrix object, and utility classes for data reading and writing have been incorporated.

## Functionality

- Construction of matrices from data files, user input, or pre-populated double[][] arrays
- Calculates and updates properties such as determinants, dimensionality, and invertability.
- Methods for determinants, transposes, manual reassignment of entries, display, equality, among others
- Regression class continues to deliver fast and reliable linear LSR
- Reader and writer classes to collect and write tab delimited data

## Usage

1. Compile the Java program: `javac Regression.java`
2. Run the program and follow prompts to indicate the path to your data file and the order of model you wish to fit
3. Choose whether to write your coefficients to a .txt file or simply display the results
4. Additional classes can be written for user-desired functionality using the methods provided in the matrix object

Example:
```bash
java Regression
```
## Requirements

- JDK21 installed on your system
- An input data file with two columns separated by tabs (Regression)

## Data Format

Input data for regression can contain headers but does not have to. Each row should contain two entries separated by a tab
```bash
x      y
1.0    2.1
3.2    4.3
5.4    6.5
```
## Output

The Regression class produces 3 model components:

- Phi: The sum of squared residuals for this order of model
- RSQ: The R squared value of the model
- Linear coefficients for the model function
- Option to store in the file "coefficients.txt"

The Matrix object is also compatible with the file writer and reader, and has its own function showMat() to display matrices on the fly

## Future additions

- Eigenvectors and Eigenvalues
- Dot products
- Cross products
- Scalar multiplication
- Spline interpolation
- Iterative solving for transcendental systems

## Authors

This program was collectively written by Gage, Gabe, Mo, and Kellen
