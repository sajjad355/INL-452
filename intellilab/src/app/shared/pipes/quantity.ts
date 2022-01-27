import { Pipe, PipeTransform } from '@angular/core';
/*
 * Raise the value exponentially
 * Takes an exponent argument that defaults to 1.
 * Usage:
 *   value | exponentialStrength:exponent
 * Example:
 *   {{ 2 | exponentialStrength:10 }}
 *   formats to: 1024
*/
@Pipe({name: 'quantity'})
export class QuantityPipe implements PipeTransform {
  transform(value: number): number {
    // let exp = parseFloat(exponent);
    return Math.floor(value);
  }
}