    package simulator.politicalCompassElements;

    import java.util.List;
    import java.util.Map;

    import simulator.Pair;

    /** Represents an elector in the political compass */
    public class Elector extends ElementOnCompass implements JudgingElement {

        /**
         * Increments preferences of the elector's preferred political subject(s)
         * @param preferences hashmap containing all the preferences
         * @param politicalParties list of all the political parties candidating
         * @param candidates list of all the candidates
         * @param minToVote how much has the elector to sympathize with a candidate to vote them independently of the political party they are in
         */
        public void expressOpinion(Map<Pair<? extends JudgingElement, ? extends JudgedElement>, Double> preferences,
                List<PoliticalParty> politicalParties, List<Candidate> candidates, double minToVote) {
            Candidate preferredCandidate = findPreferredSubject(preferences, candidates);
            Pair<? extends JudgingElement, ? extends JudgedElement> key = new Pair<>(this, preferredCandidate);
            if (preferredCandidate != null && preferences.get(key) >= minToVote) {
                preferredCandidate.addPreference();
                if (preferredCandidate.getParty() != null) {
                    preferredCandidate.getParty().addPreference();
                }
            } else {
                PoliticalParty preferredPoliticalParty = findPreferredSubject(preferences, politicalParties);
                preferredCandidate = findPreferredSubject(preferences, preferredPoliticalParty.getMembers());
                preferredPoliticalParty.addPreference();
                preferredCandidate.addPreference();
            }
            return;
        }

        /**
         * Returns elector's preferred political subject from the given list
         * @param preferences hashmap of all the preferences
         * @param politicalSubjects list of political subjects to choose from
         * @return elector's preferred political subject from the given list
         */
        public <T extends PoliticalSubject> T findPreferredSubject(
                Map<Pair<? extends JudgingElement, ? extends JudgedElement>, Double> preferences,
                List<T> politicalSubjects) {
            Double maxValue = 0.0;
            T preferredPoliticalSubject = null;
            for (T politicalSubject : politicalSubjects) {
                Pair<? extends JudgingElement, ? extends JudgedElement> key = new Pair<>(this, politicalSubject);
                Double value = preferences.get(key);
                if (value > maxValue) {
                    maxValue = value;
                    preferredPoliticalSubject = politicalSubject;
                }
            }
            return preferredPoliticalSubject;
        }
    }
