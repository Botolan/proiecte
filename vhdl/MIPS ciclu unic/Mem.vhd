library IEEE;
use IEEE.STD_LOGIC_1164.ALL;


entity Mem is
    Port(MemWrite: in STD_LOGIC;
         clk: in STD_LOGIC;
         AluIn: in STD_LOGIC_VECTOR(15 downto 0);
         RD2: in STD_LOGIC_VECTOR(15 downto 0);
         MEMData: out STD_LOGIC_VECTOR(15 downto 0);
         AluOut: out STD_LOGIC_VECTOR(15 downto 0)); 
end Mem;

architecture Behavioral of Mem is

begin

    MEM: entity work.RAM port map(address => AluIn, wd => RD2, we => MemWrite, clk => clk, rd => MemData);
    AluOut <= AluIn;
    
end Behavioral;
