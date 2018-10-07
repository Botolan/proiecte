
----------------------------------------------------------------------------------


library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
entity EX is
    Port(PC1: in STD_LOGIC_VECTOR(15 downto 0);
         RD1: in STD_LOGIC_VECTOR(15 downto 0);
         ALUSrc: in STD_LOGIC;
         RD2: in STD_LOGIC_VECTOR(15 downto 0);
         Ext_imm: in STD_LOGIC_VECTOR(15 downto 0);
         sa: in STD_LOGIC;
         func: in STD_LOGIC_VECTOR(2 downto 0);
         ALUOp: in STD_LOGIC_VECTOR(2 downto 0);
         BranchAddress: out STD_LOGIC_VECTOR(15 downto 0);
         Zero: out STD_LOGIC;
         ALURes1: out STD_LOGIC_VECTOR(15 downto 0));
end EX;

architecture Behavioral of EX is

SIGNAL mux: STD_LOGIC_VECTOR(15 downto 0);
SIGNAL ResAluControl: STD_LOGIC_VECTOR(2 downto 0);
SIGNAL Op: STD_LOGIC_VECTOR(2 downto 0);
SIGNAL ALURes: STD_LOGIC_VECTOR(15 downto 0);

begin

    mux <= RD2 when ALUSrc = '0' else
           Ext_imm  when ALUSrc = '1';
           
    BranchAddress <= PC1 + Ext_imm;
   
   
    ALURes1 <= ALURes;

    process(func, AluOp)
    begin
        case ALUOp is
            when "000" => case func is
                            when "000" => Op <= "000";
                            when "001" => Op <= "001";
                            when "010" => Op <= "010";
                            when "011" => Op <= "011";
                            when "100" => Op <= "100";
                            when "101" => Op <= "101";
                            when "110" => Op <= "110";
                            when "111" => Op <= "111";
                           end case;
            when "001" => Op <= "000";
            when "010" => Op <= "000";
            when "011" => Op <= "001";
            when "100" => Op <= "000";
            when "101" => Op <= "000";
            when "110" => Op <= "101";
            when "111" => Op <= "100";
      end case;   

    end process;
    
    
    process(Op)
    begin
        case Op is
            when "000" => ALURes <= RD1 + mux;
            when "001" => ALUres <= RD1 - mux;
            when "010" => case sa is
                            when '0' => ALURes <= RD1;
                            when '1' => ALURes <= RD1(14 downto 0) & '0';
                          end case;
            when "011" => case sa is
                            when '0' => ALURes <= RD1;
                            when '1' => ALURes <= '0' & RD1(15 downto 1);
                          end case;
            when "100" => ALURes <= RD1 and RD2;
            when "101" => ALURes <= RD1 or RD2;
            when "110" => ALURes <= RD1 xor RD2;
            when "111" => ALURes <= RD1(7 downto 0) * RD2(7 downto 0);
       end case;    
             
       if ALURes = x"0000" then
         Zero <= '1';
       else
         Zero <= '0';
       end if; 
    end process;

end Behavioral;
