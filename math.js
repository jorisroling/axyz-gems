/** Provides a fast approximation of math functions using a Pade approximant
    continued fraction, calculated sample by sample.
    Note: This is an approximation which works on a limited range. You are
    advised to use input values only between -5 and +5 for limiting the error.

    Copied with kind permission of Jules Storer.   https://forum.juce.com/t/math-functions-in-littlefoot/33415
    This file is part of the JUCE library. Copyright (c) 2019 - ROLI Ltd.
*/


function cosh (x)
{
    const x2 = x * x;
    const numerator = -(39251520 + x2 * (18471600 + x2 * (1075032 + 14615 * x2)));
    const denominator = -39251520 + x2 * (1154160 + x2 * (-16632 + 127 * x2));
    return numerator / denominator;
}


function sinh (x)
{
    const x2 = x * x;
    const numerator = -x * (11511339840 + x2 * (1640635920 + x2 * (52785432 + x2 * 479249)));
    const denominator = -11511339840 + x2 * (277920720 + x2 * (-3177720 + x2 * 18361));
    return numerator / denominator;
}

function tanh (x)
{
    const x2 = x * x;
    const numerator = x * (135135 + x2 * (17325 + x2 * (378 + x2)));
    const denominator = 135135 + x2 * (62370 + x2 * (3150 + 28 * x2));
    return numerator / denominator;
}


function cos (x)
{
    const x2 = x * x;
    const numerator = -(-39251520 + x2 * (18471600 + x2 * (-1075032 + 14615 * x2)));
    const denominator = 39251520 + x2 * (1154160 + x2 * (16632 + x2 * 127));
    return numerator / denominator;
}

function sin (x)
{
    const x2 = x * x;
    const numerator = -x * (-11511339840 + x2 * (1640635920 + x2 * (-52785432 + x2 * 479249)));
    const denominator = 11511339840 + x2 * (277920720 + x2 * (3177720 + x2 * 18361));
    return numerator / denominator;
}

function tan (x)
{
    const x2 = x * x;
    const numerator = x * (-135135 + x2 * (17325 + x2 * (-378 + x2)));
    const denominator = -135135 + x2 * (62370 + x2 * (-3150 + 28 * x2));
    return numerator / denominator;
}

function exp (x)
{
    const numerator = 1680 + x * (840 + x * (180 + x * (20 + x)));
    const denominator = 1680 + x *(-840 + x * (180 + x * (-20 + x)));
    return numerator / denominator;
}


// Polynomial approximating arctangenet on the range -1,1.
// Max error < 0.005 (or 0.29 degrees)
function atan(z)
{
    const n1 = 0.97239411;
    const n2 = -0.19194795;
    return (n1 + n2 * z * z) * z;
}



/*function atan2(y,x)
{
    const PI   = 3.14159265358979323846264338327950288;
    const PI_2 = 6.28318530717958647692528676655900576;
    if (x != 0.0) {
        if (Math.abs(x) > Math.abs(y)) {
            const z = y / x;
            if (x > 0.0) {
                // atan2(y,x) = atan(y/x) if x > 0
                return atan(z);
            } else if (y >= 0.0) {
                // atan2(y,x) = atan(y/x) + PI if x < 0, y >= 0
                return atan(z) + PI;
            } else {
                // atan2(y,x) = atan(y/x) - PI if x < 0, y < 0
                return atan(z) - PI;
            }
        } else { // Use property atan(y/x) = PI/2 - atan(x/y) if |y/x| > 1.
            const z = x / y;
            if (y > 0.0) {
                // atan2(y,x) = PI/2 - atan(x/y) if |y/x| > 1, y > 0
                return -atan(z) + PI_2;
            } else {
                // atan2(y,x) = -PI/2 - atan(x/y) if |y/x| > 1, y < 0
                return -atan(z) - PI_2;
            }
        }
    } else {
        if (y > 0.0) { // x = 0, y > 0
            return PI_2;
        } else if (y < 0.0) { // x = 0, y < 0
            return -PI_2;
        }
    }
    return 0.0; // x,y = 0. Could return NaN instead.
}

function atan2_approximation1(y,x)
{
    //http://pubs.opengroup.org/onlinepubs/009695399/functions/atan2.html
    //Volkan SALMA

  const PI   = 3.14159265358979323846264338327950288;
  const ONEQTR_PI = PI / 4.0;
	const THRQTR_PI = 3.0 * PI / 4.0;
	let r, angle;
	const abs_y = Math.abs(y) + 1e-10;      // kludge to prevent 0/0 condition
	if ( x < 0.0 ) {
		r = (x + abs_y) / (abs_y - x);
		angle = THRQTR_PI;
	} else {
		r = (x - abs_y) / (x + abs_y);
		angle = ONEQTR_PI;
	}
	angle += (0.1963 * r * r - 0.9817) * r;
	if ( y < 0.0 )
		return -angle;     // negate if in quad III or IV
	else
		return angle;


}
*/

function atan2(y, x ) //_approximation2
{
  const PI   = 3.14159265358979323846264338327950288;
	const PIBY2 = 1.5707963;
	if ( x == 0.0 ) {
		if ( y > 0.0 ) return PIBY2;
		if ( y == 0.0 ) return 0.0;
		return -PIBY2;
	}
	let atan;
	const z = y/x;
	if ( Math.abs( z ) < 1.0 ) {
		atan = z/(1.0 + 0.28*z*z);
		if ( x < 0.0 ) {
			if ( y < 0.0 ) return atan - PI;
			return atan + PI;
		}
	} else {
		atan = PIBY2 - z/(z*z + 0.28);
		if ( y < 0.0 ) return atan - PI;
	}
	return atan;
}




function DegreesToX(degrees, radius, origin)
{
    const radians = degrees * Math.PI / 180.0;

    return cos(radians) * radius + origin;
}

function DegreesToY(degrees, radius, origin)
{
    const radians = degrees * Math.PI / 180.0;

    return sin(radians) * radius + origin;
	}


function XYToDegrees(x,y,originX,originY)
{
    const deltaX = originX - x;
    const deltaY = originY - y;

/*    const radAngle = atan2(deltaY, deltaX);*/
    const radAngle = atan2(y, x);
		console.log(`radAngle ${radAngle}`)
    const degreeAngle = radAngle * (180.0 / Math.PI);
		console.log(`degreeAngle ${degreeAngle}`)

    return (180.0 - degreeAngle);
}





const rads = 6.2831853072 / 2

for (let x = 0; x <= rads +0.1; x += 0.1) {
	console.log(`cos(${x}) = ${cos(x)}   vs  ${Math.cos(x)}  delta  ${Math.abs(Math.cos(x)-cos(x)).toFixed(7)}`)
}

for (let x = 0; x <= rads +0.1; x += 0.1) {
	console.log(`sin(${x}) = ${sin(x)}   vs  ${Math.sin(x)}  delta  ${Math.abs(Math.sin(x)-sin(x)).toFixed(7)}`)
}

/*for (let x = -5; x <= 5; x++) {
	for (let y = -5; y <= 5; y++) {
*/
for (let x = -1.0; x <= 1.0; x += 0.1) {
	for (let y = -1.0; y <= 1.0; y += 0.1) {
		console.log(`atan2(${y},${x}) = ${atan2(y,x)}   vs  ${Math.atan2(y,x)}  delta  ${Math.abs(Math.atan2(y,x)-atan2(y,x)).toFixed(7)}`)
//		console.log(`atan2_approximation1(${y},${x}) = ${atan2_approximation1(y,x)}   vs  ${Math.atan2(y,x)}  delta  ${Math.abs(Math.atan2(y,x)-atan2_approximation1(y,x)).toFixed(7)}`)

/*		console.log(`atan2_approximation2(${y},${x}) = ${atan2_approximation2(y,x)}   vs  ${Math.atan2(y,x)}  delta  ${Math.abs(Math.atan2(y,x)-atan2_approximation2(y,x)).toFixed(7)}`)*/
		const degrees = XYToDegrees(x,y,0,0)

		const nx = DegreesToX(degrees, 1.0, 0)
		const ny = DegreesToY(degrees, 1.0, 0)

		const dx = Math.abs(x - nx)
		const dy = Math.abs(y - ny)
		console.log(`X = ${x}  degrees = ${degrees}  NX = ${nx}  DX = ${dx}  `)
		console.log(`Y = ${y}  degrees = ${degrees}  NY = ${ny}  DY = ${dy}  `)
		console.log(Math.PI)
/*		process.exit()*/
	}
}


function sqrt(n) {
	if (!n) return 0;
	let t;

	let squareRoot = n / 2;

	do {
		t = squareRoot;
		squareRoot = (t + (n / t)) / 2;
	} while ((t - squareRoot) != 0);

	return squareRoot;
}

console.log(Math.sin(90 * Math.PI / 180))
console.log(sin(90 * Math.PI / 180))

function calcAngleDegrees(x, y) {
/*  return Math.atan2(y, x) * (180 / Math.PI);*/
  return atan2(y, x) * (180 / Math.PI);
}

function radius(x,y)
{
/*	return Math.sqrt((x*x) + (y*y))*/
	return sqrt((x*x) + (y*y))
}

for (let x = 0; x < 15; x += 1) {
	for (let y = 0; y < 15; y += 1) {
		const deg = calcAngleDegrees(x, y)
		const rad = radius(x,y)
		const X = DegreesToX(deg,rad,0)
		const Y = DegreesToY(deg,rad,0)}
		console.log(`JJR: x:${x}  y:${y}   deg:${deg.toFixed(2)}  radius:${rad.toFixed(2)}  X:${X.toFixed(2)}  Y:${Y.toFixed(2)}`);
	}
}

console.log(calcAngleDegrees(5, 5));
//expected output: 45

console.log(calcAngleDegrees(10, 10));
//expected output: 45

console.log(calcAngleDegrees(0, 10));
//expected output: 90
