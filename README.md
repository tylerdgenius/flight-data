# Virgin Holidays ~ Flight Information Display
## Rules
You can write the application in any language you see fit, bearing in mind that its sole intention is to showcase your
skills for the applied-for position. 

In addition you should adhere to the following conditions:

1) It must be easy to run using libraries & tools commonly available on a development machine. 
1) Clear instructions for how to build and run the application should be included within the code, e.g. `README.md`
1) The code must be your own work. If you use another author's code snippet, it must be clearly commented and attributed.
1) The flight data cannot be changed, and must be loaded from the CSV file, so it can easily be replaced with another file.

## What it should do
The application should allow the user to select or input any date, of any year, resulting in the display of flights on
that day, displayed in chronological order -- a Flight Information Display.

The interface can be web-based, command-line or otherwise, but as already mentioned, you should choose the approach you
feel demonstrates your expertise and suitability for the position.

## Supporting Data
The [flight data](flights.csv) is a simple comma-separated file containing the following:

| Departure Time | Destination | Destination Airport IATA | Flight No | Sun | Mon | Tue | Wed | Thu | Fri | Sat
| :--- | :--- | :--- | :--- | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| 09:00 | Antigua | ANU | VS033 |  |  | `x` |  |  |  | 
| 10:00 | Antigua | ANU | VS033 |  |  |  |  | `x` |  | `x`
| 11:05 | Barbados | BGI | VS029 | `x` | `x` | `x` | `x` | `x` | `x` | `x`
| 12:20 | Cancun | CUN | VS093 |  |  | `x` |  |  |  | 
| 09:00 | Grenada | GND | VS089 |  | `x` |  |  |  |  | 
| 10:10 | Grenada | GND | VS089 |  |  |  |  |  | `x` | 
| 10:15 | Havana | HAV | VS063 |  |  | `x` |  |  |  | 
| 10:15 | Havana | HAV | VS063 |  | `x` |  |  | `x` |  | 
| 10:15 | Las Vegas | LAS | VS043 | `x` |  |  |  |  | `x` | `x`
| 10:25 | Las Vegas | LAS | VS043 |  |  |  |  | `x` |  | 
| 10:35 | Las Vegas | LAS | VS043 |  | `x` | `x` | `x` |  |  | 
| 15:35 | Las Vegas | LAS | VS044 | `x` | `x` | `x` | `x` | `x` | `x` | `x`
| 12:25  | Montego Bay | MBJ | VS065 |  |  |  | `x` |  |  | 
| 12:40 | Montego Bay | MBJ | VS065 | `x` |  |  |  |  |  | 
| 10:10 | Orlando | MCO | VS049 | `x` |  |  |  |  |  | 
| 10:15 | Orlando | MCO | VS027 |  |  |  | `x` |  |  | 
| 11:00 | Orlando | MCO | VS027 |  | `x` |  |  |  |  | 
| 11:10 | Orlando | MCO | VS049 |  | `x` |  |  |  |  | 
| 11:20 | Orlando | MCO | VS027 |  |  |  |  |  | `x` | `x`
| 11:35 | Orlando | MCO | VS027 |  |  |  |  | `x` |  | 
| 11:45 | Orlando | MCO | VS027 | `x` |  | `x` |  |  |  | 
| 11:45 | Orlando | MCO | VS049 |  |  |  | `x` |  |  | 
| 13:00 | Orlando | MCO | VS015 | `x` | `x` | `x` | `x` | `x` | `x` | `x`
| 09:00 | St Lucia | UVF | VS089 |  | `x` |  |  |  |  | 
| 09:00 | St Lucia | UVF | VS097 | `x` |  |  |  |  |  | 
| 10:10 | St Lucia | UVF | VS089 |  |  |  |  |  | `x` | 
| 09:00 | Tobago | TAB | VS097 | `x` |  |  |  |  |  |

The ``x`` denotes days that the flight operates. 