package TimKiemMu;

import java.util.*;

public class MillionnaireAndRobber {
    static class State{
        int mLeft, cLeft, boat;
        State current;

        public State (int mLeft, int cLeft, int boat, State current){
            this.mLeft = mLeft;
            this.cLeft = cLeft;
            this.boat = boat;
            this.current = current;
        }

        public boolean isValid(){
            if (cLeft > 3 || cLeft < 0 || mLeft > 3 || mLeft < 0){
                return false;
            }

            if (mLeft > 0 && mLeft < cLeft){
                return false;
            }

            int mRight = 3 - mLeft;
            int cRight = 3 - cLeft;
            if (mRight > 0 && mRight < cRight){
                return false;
            }
            return true;
        }

        public boolean isGoal(){
            if (cLeft == 0 && mLeft == 0 && boat == 1){
                return true;
            }
            return false;
        }

        @Override
        public boolean equals(Object object){
            State state = (State) object;
            return state.mLeft == this.mLeft && state.cLeft == this.cLeft && state.boat == this.boat;
        }

        @Override
        public int hashCode(){
            return Objects.hash(mLeft, cLeft, boat);
        }

        @Override
        public String toString() {
            String b = (boat == 0) ? "Thuyền ở Trái" : "Thuyền ở Phải";
            return String.format("Bờ Trái: (%d M, %d C) | %s", mLeft, cLeft, b);
        }

    }

    public static void main(String[] args) {
        int [][] actions = {
                {2, 0}, // 2 M
                {0, 2}, // 2 C
                {1, 1}, // 1 M 1 C
                {1, 0}, // 1 M
                {0, 1}  // 1 C
        };

        State initialState = new State(3, 3, 0, null);
        Queue<State> queue = new LinkedList<>();
        Set<State> visited = new HashSet<>();

        queue.add(initialState);
        visited.add(initialState);

        while (!queue.isEmpty()){
            State currentState = queue.poll();

            if (currentState.isGoal()){
                printSolution(currentState);
                return;
            }

            for (int []action : actions){
                int mMove = action[0];
                int cMove = action[1];

                State next;

                if (currentState.boat == 0){
                    next = new State(currentState.mLeft - mMove, currentState.cLeft - cMove, 1, currentState);
                }
                else{
                    next = new State(currentState.mLeft + mMove, currentState.cLeft + cMove, 0, currentState);
                }

                if (!visited.contains(next)&& next.isValid()){
                    queue.add(next);
                    visited.add(next);
                }
            }
        }
     }

    public static void printSolution(State solution){
        Stack<State> path = new Stack<>();
        State curr = solution;

        while (curr != null){
            path.push(curr);
            curr = curr.current;
        }

        System.out.println("--- LỜI GIẢI BÀI TOÁN ---");
        int step = 0;
        while (!path.isEmpty()) {
            System.out.println("Bước " + (step++) + ": " + path.pop());
        }
    }
}
