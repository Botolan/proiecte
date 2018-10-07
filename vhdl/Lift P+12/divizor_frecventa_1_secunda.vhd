library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.STD_LOGIC_UNSIGNED.all;
use IEEE.STD_LOGIC_ARITH.all;

entity DIVIZOR_1_SEC is
	port(CLOCK:in STD_LOGIC;
	CLOCK_DIVIZAT:out STD_LOGIC);
end entity DIVIZOR_1_SEC;


architecture F_DIVIZOR_1_SEC of DIVIZOR_1_SEC is

begin
	process(CLOCK)
	variable NUMARATOR:STD_LOGIC_VECTOR(26 downto 0) := "000000000000000000000000000";
    begin
        if CLOCK = '1' and CLOCK'EVENT then
            if NUMARATOR = "101111101011110000100000000" then
                CLOCK_DIVIZAT <= '1';
				NUMARATOR := "000000000000000000000000000"; 
			else
			     NUMARATOR := NUMARATOR + '1';
			     CLOCK_DIVIZAT <= '0';
		    end if;
		end if;
	end process;	
end architecture F_DIVIZOR_1_SEC;

	