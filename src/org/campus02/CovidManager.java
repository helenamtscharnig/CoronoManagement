package org.campus02;

import java.util.ArrayList;
import java.util.HashMap;

public class CovidManager {

    public static void main(String[] args) {

        /*Incidence stmk20211111 = new Incidence(
                "Steiermark", "2021-11-11", 1300);
        Incidence stmk20211110 = new Incidence(
                "Steiermark", "2021-11-10", 1200);
        Incidence wien20211111 = new Incidence(
                "Wien", "2021-11-11", 5631);
        Incidence wien20211110 = new Incidence(
                "Wien", "2021-11-10", 4832);
        Incidence tirol20211111 = new Incidence(
                "Tirol", "2021-11-11", 999);*/

        ArrayList<Incidence> recordedValues = new ArrayList<>();
        recordedValues = DataProvider.getData();

        System.out.println("Count of recorded Records: " + recordedValues.size());

        // Count der gesamten Fälle
        int countTotalIncidences = 0;
        for (int i = 0; i < recordedValues.size(); i++) {
            Incidence oneValue = recordedValues.get(i);
            //countTotalIncidences += oneValue.getNumber();
            countTotalIncidences = countTotalIncidences + oneValue.getNumber();
        }
        System.out.println("Bisher in Österreich: " + countTotalIncidences + " Fälle");

        countTotalIncidences = 0;
        for (Incidence incidence : recordedValues) {
            // für jeden Incidence in recordedValues
            countTotalIncidences += incidence.getNumber();
        }
        System.out.println("Bisher in Österreich: " + countTotalIncidences + " Fälle");

        int countTotalIncidencesStmk = 0;
        // iter
        for (Incidence recordedValue : recordedValues) {
            if (recordedValue.getState().equals("Steiermark"))
                countTotalIncidencesStmk += recordedValue.getNumber();
        }
        System.out.println("countTotalIncidencesStmk = " + countTotalIncidencesStmk);
        System.out.println("countTotalIncidencesDate" + groupByDate(recordedValues));
        System.out.println("totalIncidencesState = " + groupByState(recordedValues));
        System.out.println("findHighestValue = " + findHighestValue(recordedValues));
        System.out.println("getAverageValue = " + getAverageValueAfter(recordedValues, "2021-10-01"));
        System.out.println("findDayStateWithHighestValues(recordedValues) = " + findDayStateWithHighestValues(recordedValues));

    }

    public static HashMap<String, Integer> groupByState(ArrayList<Incidence> recordedValues) {
        // Anzahl der Fälle je Bundesland
        HashMap<String, Integer> totalIncidencesState = new HashMap<>();
        for (Incidence oneElement : recordedValues) {
            /*
                1. Ist mein Key bereits in der Liste
                    2. wenn nein, dann muss ich den Key aufnehmen
                    3. wenn ja, dann muss ich den Wert auslesen, summieren, neu ablegen
             */
            if (totalIncidencesState.containsKey(oneElement.getState())){
                // ja, Fall 3
                int currentValue = totalIncidencesState.get(oneElement.getState());
                currentValue += oneElement.getNumber();
                totalIncidencesState.put(oneElement.getState(), currentValue);
            }
            else {
                // nein, Fall 2
                totalIncidencesState.put(oneElement.getState(),
                        oneElement.getNumber());
            }
        }
        return totalIncidencesState;
    }

    public static HashMap<String, Integer> groupByDate(ArrayList<Incidence> recordedValues) {
        // Summe der Fälle je Tag
        HashMap<String, Integer> result = new HashMap<>();
        for (Incidence oneElement : recordedValues) {
            if (result.containsKey(oneElement.getDate())){
                result.put(oneElement.getDate(),
                        result.get(oneElement.getDate() + oneElement.getNumber()));
            }
            else {
                result.put(oneElement.getDate(), oneElement.getNumber());
            }

        }
        return result;
    }

    public static int findHighestValue(ArrayList<Incidence> recordedValues) {
        // Es soll der höchste gemeldete Wert gefunden werden
        int maxValue = Integer.MIN_VALUE;

        for (Incidence oneElement : recordedValues){
            if (oneElement.getNumber() > maxValue){
                maxValue = oneElement.getNumber();
            }
        }

        return maxValue;
    }

    public static int getAverageValueAfter(ArrayList<Incidence> recordedValues, String date) {
        // Es soll der durchschnittliche Wert nach einem bestimmten Datum gefunden werden
        // date ist beispielsweise 2021-08-01
        int counter = 0;
        int sum = 0;

        for (Incidence oneElement : recordedValues) {
            if(oneElement.getDate().compareTo(date) > 0){
                counter++;
                sum += oneElement.getNumber();
            }
        }
        if (counter == 0)
            return -1;
        return sum / counter;
    }

    public static Incidence findDayStateWithHighestValues(ArrayList<Incidence> recordedValues) {
        // Es soll die Meldung (state/date) mit dem höchsten Wert gefunden werden
        for (Incidence oneElement : recordedValues) {
            if (oneElement.getNumber() == findHighestValue(recordedValues)){
                return oneElement;
            }
        }
        return null;
    }
}
