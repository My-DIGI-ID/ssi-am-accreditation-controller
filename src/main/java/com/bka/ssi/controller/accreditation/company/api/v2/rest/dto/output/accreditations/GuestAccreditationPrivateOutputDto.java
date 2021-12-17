/*
 * Copyright 2021 Bundesrepublik Deutschland
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations;

import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.parties.GuestPrivateOutputDto;

/**
 * The type Guest accreditation private output dto.
 */
public class GuestAccreditationPrivateOutputDto extends GuestAccreditationOpenOutputDto {

    // ToDo - should be renamed and not overriden, so GuestAccreditationPrivateOutputDto returns
    //  open and private information
    private GuestPrivateOutputDto guest;

    /**
     * Instantiates a new Guest accreditation private output dto.
     */
    public GuestAccreditationPrivateOutputDto() {
    }

    @Override
    public GuestPrivateOutputDto getGuest() {
        return guest;
    }

    /**
     * Sets guest.
     *
     * @param guest the guest
     */
    public void setGuest(
        GuestPrivateOutputDto guest) {
        this.guest = guest;
    }
}
