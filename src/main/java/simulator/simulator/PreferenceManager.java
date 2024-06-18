package simulator.simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import simulator.Pair;
import simulator.politicalCompassElements.Candidate;
import simulator.politicalCompassElements.Elector;
import simulator.politicalCompassElements.JudgedElement;
import simulator.politicalCompassElements.JudgingElement;
import simulator.politicalCompassElements.PoliticalParty;
import simulator.politicalCompassElements.PoliticalSubject;

/** Manages the preferences of electors towards political parties and candidates */
public class PreferenceManager {
    private Simulator simulator;

    protected PreferenceManager(Simulator simulator) {
        this.simulator = simulator;
    }

    private void waitForThreads(List<Thread> threads) {
        for (Thread th : threads) {
            try {
                th.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        threads.clear();
        return;
    }

    /** Sets preferences of all the given political subjects to 0 */
    protected <T extends PoliticalSubject> void resetPreferences(List<T>... lists) {
        for (List<T> list : lists) {
            for (T element :list) {
                element.resetPreferences();
            }
        }
        return;
    }

    /** Sets preference of a judging element towards a judged element based on their distance on the political compass */
    protected <T extends JudgingElement, U extends JudgedElement> void setPreference(Map<Pair<? extends JudgingElement, ? extends JudgedElement>, Double> preferences, T judgingElement, U judgedElement) {
        double value = (Math.sqrt(800) - judgingElement.distanceFrom(judgedElement)) * (100 / Math.sqrt(800));
        Pair<? extends JudgingElement, ? extends JudgedElement> key = new Pair<>(judgingElement, judgedElement);
        preferences.put(key, value);
        return;
    }

    /** Sets all electors' preferences towards political parties and candidates */
    protected <T extends PoliticalSubject> void setPreferences() {
        Map<Pair<? extends JudgingElement, ? extends JudgedElement>, Double> preferences = simulator.getPreferences();
        List<PoliticalParty> politicalParties = simulator.getPoliticalParties();
        List<Candidate> candidates = simulator.getCandidates();
        List<Elector> electors = simulator.getElectors();
        List<Thread> threads = new ArrayList<>();
        int maxThreads = 50;
        double minToVote = simulator.getMinToVote();

        resetPreferences((List<T>) politicalParties, (List<T>) candidates);

        for (PoliticalParty politicalParty : politicalParties) {
            Thread thread = new Thread() {
                public void run() {
                    for (Elector elector : electors) {
                        setPreference(preferences, elector, politicalParty);
                    }
                }
            };
            thread.start();
            threads.add(thread);
            if (threads.size() == maxThreads - 1) {
                waitForThreads(threads);
            }
        }

        for (Candidate candidate : candidates) {
            Thread thread = new Thread() {
                public void run() {
                    for (Elector elector : electors) {
                        setPreference(preferences, elector, candidate);
                    }
                }
            };
            thread.start();
            threads.add(thread);

            if (threads.size() == maxThreads) {
                waitForThreads(threads);
            }
        }

        waitForThreads(threads);

        for (Elector elector : electors) {
            elector.expressOpinion(preferences, politicalParties, candidates, minToVote);
        }

        return;
    }
}
