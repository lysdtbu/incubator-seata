/*
 *  Copyright 1999-2019 Seata.io Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package io.seata.saga.statelang.validator.impl;

import io.seata.saga.statelang.domain.State;
import io.seata.saga.statelang.domain.StateMachine;
import io.seata.saga.statelang.parser.utils.StateMachineUtils;

import java.util.Set;

/**
 * Rule to check if all the state name exists
 *
 * @author ptyin
 */
public class StateNameExistsRule extends AbstractRule {

    @Override
    public boolean validate(StateMachine stateMachine) {
        for (State state: stateMachine.getStates().values()) {
            Set<String> subsequentStates = StateMachineUtils.getAllPossibleSubsequentStates(state);
            for (String subsequentState: subsequentStates) {
                if (stateMachine.getState(subsequentState) == null) {
                    hint = String.format("Subsequent state [%s] of [%s] does not exist", subsequentState, state);
                    return false;
                }
            }
        }
        return true;
    }
}
