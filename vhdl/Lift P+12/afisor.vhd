library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;


entity AFISOR is
Port ( CLOCK : in STD_LOGIC;
           CIFRA0 : in STD_LOGIC_VECTOR (3 downto 0);
           CIFRA1 : in STD_LOGIC_VECTOR (3 downto 0);
		   CIFRA2 : in STD_LOGIC_VECTOR (3 downto 0);
		   CIFRA3 : in STD_LOGIC_VECTOR (3 downto 0);
           ANOD : out STD_LOGIC_VECTOR (3 downto 0);
           CATOD : out STD_LOGIC_VECTOR (6 downto 0));
end AFISOR;


architecture F_AFISOR of AFISOR is
signal NUMARATOR : STD_LOGIC_VECTOR (15 downto 0);
signal ELEMENT : STD_LOGIC_VECTOR (3 downto 0);
begin
    process(CLOCK)
    begin
         if CLOCK = '1' and CLOCK'event then
              NUMARATOR <= NUMARATOR + 1;
          end if;
    end process;
    
    process(NUMARATOR(15 downto 14))
    begin
        if NUMARATOR(15 downto 14)="00" then
            ANOD<="1110";
        elsif NUMARATOR(15 downto 14)="01" then
            ANOD<="1101";
        elsif NUMARATOR(15 downto 14)="10" then
            ANOD<="1011";
        else ANOD<="0111";
        end if;
    end process;
    
    process(NUMARATOR(15 downto 14))
    begin
         if NUMARATOR(15 downto 14)="00" then
               ELEMENT<=CIFRA3;
          elsif NUMARATOR(15 downto 14)="01" then
               ELEMENT<=CIFRA2;
          elsif NUMARATOR(15 downto 14)="10" then
               ELEMENT<=CIFRA1;
          else ELEMENT<=CIFRA0;
        end if;    
     end process;
        
     process
     begin
      case ELEMENT is 
		 when "0000" => CATOD<="1000000";
         when "0001" => CATOD<="1111001";   --1
         when "0010" => CATOD<="0100100";   --2
         when "0011" => CATOD<="0110000";   --3
         when "0100" => CATOD<="0011001";   --4
         when "0101" => CATOD<="0010010";   --5
         when "0110" => CATOD<="0000010";   --6
         when "0111" => CATOD<="1111000";   --7
         when "1000" => CATOD<="0000000";   --8
         when "1001" => CATOD<="0010000";   --9
         when others => CATOD<="0111111";   --"-"
       end case;
       end process;            
end F_AFISOR;
